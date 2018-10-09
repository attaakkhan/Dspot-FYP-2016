#!/usr/bin/python
# -*- coding: utf-8 -*-
from django.shortcuts import render, get_object_or_404
from django.http import HttpResponseRedirect, HttpResponseForbidden
from devices.models import *

# Create your views here.

from devices.switch_f import *
from django.template import RequestContext
from django.shortcuts import render_to_response
import urllib2
import httplib
import socket
from django.db import transaction
from devices.netduino_con import *

from itertools import chain
__author__ = 'Attaullah Khan'


def my_devices(request):
    d_list = list(chain(
        request.user.switch_man_u.all(),
        request.user.switch_sen_u.all(),
        request.user.switch_tim_u.all(),
        request.user.alarm_clock_u.all(),
        request.user.wareabout_u.all(),
        request.user.door_bell_u.all(),
        ))
    df_list = []

    if request.POST:
        if request.POST.get('remnetduino'):
            print request.POST['remove']
            request.user.netduino_user.get(pk=request.POST['remove'
                    ]).delete()
    for a in d_list:
        if a.users.all():
            df_list = list(chain(df_list, [a]))
    f_list = list(chain(
        request.user.switch_man_us.all(),
        request.user.switch_sen_us.all(),
        request.user.switch_tim_us.all(),
        request.user.alarm_clock_us.all(),
        request.user.wareabout_us.all(),
        request.user.door_bell_us.all(),
        ))
    mynetduinos = request.user.netduino_user.all()
    myhisnetduinos = list()
    l1 = list()
    for i in mynetduinos:
        print i.wareabout, 'ssssssssssssss'
        for j in i.switches.all():
            if j.users.all():
                l2 = list()
                l2.append(i)
                if set(l2) - set(myhisnetduinos):
                    myhisnetduinos.append(i)

        if i.wareabout.users.all():
            print i
            l2 = list()
            l2.append(i)
            if set(l2) - set(myhisnetduinos):
                myhisnetduinos.append(i)
    hisnetduinos = None
    l_f = list()
    for i in f_list:
        if i.users.filter(username=request.user.username):

            l_f = list(chain(l_f, i.netduino_switches.all()))

    hisnetduinos = list(set(l_f))
    l1 = list()
    print df_list, 'dfdfdfd'
    for i in df_list:
        print 'dflist'
        if i.users.filter(username=request.user.username):
            l1 = list(chain(df_list, i.netduino_switches.all()))
        l1 = list(set(l1))
        myhisnetduinos = l1
    return render(request, 'devices/mydevices.html',
                  {'d_list': mynetduinos, 'f_list': myhisnetduinos,
                  'df_list': hisnetduinos})


def add_device(request):
    if request.method == 'POST':
        if request.POST['rrr'] == 'Add Mac':
            form = Bt_addr_f(request.POST or None)
        elif request.POST['rrr'] == 'Add Ip':
            form = Network_addr_f(request.POST or None)
        elif request.POST['rrr'] == 'Add Door Bell':
            form = Door_bell_f(request.POST or None, request.user)
        elif request.GET['rrr'] == 'Add Manual Switch':
            form = Switch_man_f(request.POST or None)
            if form.is_valid():
                f = form.save(commit=False)
                f.user = request.user
                f.save()
                return HttpResponseRedirect('/adddevice/')
    else:
        form = Bt_addr_f(request.POST or None)
    return render(request, 'devices/device_add.html', {'form': form})


def add_mac(request, id=None):
    if id:
        dev = get_object_or_404(Bt_addr, pk=id)
        if dev.user != request.user:
            return HttpResponseForbidden()
    else:
        dev = None
    if request.POST:
        form = Bt_addr_f(request.POST, instance=dev)
        if form.is_valid():
            f = form.save(commit=False)
            f.user = request.user
            f.save()
            return HttpResponseRedirect('/')
    else:
        form = Bt_addr_f(request.POST or None, instance=dev)
    return render(request, 'devices/device_add.html', {'form': form})


def add_net(request, id=None):
    if id:
        dev = get_object_or_404(Network_addr, pk=id)
        if dev.user != request.user:
            return HttpResponseForbidden()
    else:
        dev = None
    if request.method == 'POST':
        form = Network_addr_f(request.POST, instance=dev)
        if form.is_valid():
            f = form.save(commit=False)
            f.user = request.user
            f.save()
            return HttpResponseRedirect('/')
    else:
        form = Network_addr_f(request.POST or None, instance=dev)
    return render(request, 'devices/device_add.html', {'form': form})


def add_bell(request, id=None):
    if id:
        dev = get_object_or_404(Door_bell, pk=id)
        if dev.user != request.user:
            return HttpResponseForbidden()
    else:
        dev = None
    if request.method == 'POST':

        form = Door_bell_f(request.POST or None, user=request.user,
                           instance=dev)
        if form.is_valid():
            f = form.save(commit=False)
            f.user = request.user
            f.d_type = 'bell'
            f.save()
            form.save_m2m()
            return HttpResponseRedirect('/')
    else:
        form = Door_bell_f(request.POST or None, user=request.user,
                           instance=dev)
    return render(request, 'devices/device_add.html', {'form': form})

def add_netduino(request, id=None):
    this = True
    if id:
        dev = get_object_or_404(Netduino, pk=id)
        if dev.user != request.user:
            return HttpResponseForbidden()
    else:
        dev = None
    status = None
    nets = None
    if request.method == 'POST':
        if request.POST.get('fromip'):
            this = False
            nets = request.user.network_addr_set.all()
            form = None
        if request.POST.get('select2'):
            form = Netduino_f()
            ip = \
                request.user.network_addr_set.get(pk=request.POST['select1'
                    ])
            net_duino = \
                request.user.netduino_user.filter(network_addr=ip)
            if net_duino:
                net_duino = net_duino[0]
                net_duino.switches.clear()
            else:
                net_duino = Netduino(network_addr=ip)
            print net_duino.network_addr
            res = getalldevices(request, net_duino)

            if res == 'OK':
                form = Netduino_f(instance=net_duino)
                status = 'Netduino Connected'
                this = True
                net_duino.refresh_from_db()
                return HttpResponseRedirect('/netduino/'
                        + str(net_duino.id))
            else:
                status = 'Netduino Not Connected'
        else:
            form = Netduino_f(request.POST or None, instance=dev)
            if form.is_valid():
                f = form.save(commit=False)
                f.user = request.user
                f.d_name = f.network_addr.name
                f.save()
                form.save_m2m()
                res = createdevices(request.user, f)
                if res == 'OK':
                    status = 'Netduino Connected'
                    return HttpResponseRedirect('/netduino/'
                            + str(f.id))
                else:
                    status = \
                        'Could not create CHeck your Network Connection with netduino'
    else:
        form = Netduino_f(request.POST or None, instance=dev)
    return render(request, 'devices/netduino_add.html', {
        'form': form,
        'this': this,
        'nets': nets,
        'status': status,
        })


def add_bell(request, id=None):
    print id
    if id:
        dev = get_object_or_404(Door_bell, pk=id)
        if dev.user != request.user:
            return HttpResponseForbidden()
    else:
        dev = None
    if request.method == 'POST':

        form = Door_bell_f(request.POST or None, user=request.user,
                           instance=dev)
        if form.is_valid():
            f = form.save(commit=False)
            f.user = request.user
            f.d_type = 'bell'
            f.save()
            form.save_m2m()
            return HttpResponseRedirect('/')
    else:

        form = Door_bell_f(request.POST or None, user=request.user,
                           instance=dev)

    return render(request, 'devices/device_add.html', {'form': form})


def add_wareabout(request, id=None):
    if id:
        dev = get_object_or_404(Wareabout, pk=id)
        if dev.user != request.user:
            return HttpResponseForbidden()
    else:
        dev = None
    if request.method == 'POST':
        form = Wareabout_f(request.POST or None, user=request.user,
                           instance=dev)
        if form.is_valid():
            f = form.save(commit=False)
            f.user = request.user
            f.d_type = 'wareabout'
            f.save()
            form.save_m2m()
            return HttpResponseRedirect('/')
    else:

        form = Wareabout_f(None, user=request.user, instance=dev)

    return render(request, 'devices/device_add.html', {'form': form})


def add_sw_man(request, id=None):
    if id:
        dev = get_object_or_404(Switch_man, pk=id)
        if dev.user != request.user:
            return HttpResponseForbidden()
    else:
        dev = None

    if request.method == 'POST':

        form = Switch_man_f(request.POST or None, instance=dev)
        if form.is_valid():
            f = form.save(commit=False)
            f.user = request.user
            f.d_type = 'swman'
            f.status = False
            f.save()
            return HttpResponseRedirect('/')
    else:
        form = Switch_man_f(request.POST or None, instance=dev)
    return render(request, 'devices/device_add.html', {'form': form})


def share(request, type, id):
    if type == 'swman':
        try:
            d1 = request.user.switch_man_u.get(id=id)
        except Switch_man.DoesNotExist:
            d1 = None
        try:
            d2 = request.user.switch_man_us.get(pk=id)
        except Switch_man.DoesNotExist:
            d2 = None
        if d1:
            d = d1
        elif d2:
            d = d2
        else:
            d = None
            return HttpResponseForbidden()
        if request.user != d.user:
            return HttpResponseForbidden()
        if request.method == 'POST':
            if request.POST.get('share2'):
                if request.POST['share2'] == 'unshare':
                    d.users.remove(d.users.get(username=request.POST['share1'
                                   ]))
                elif request.POST['share2'] == 'share':
                    if d.user \
                        == User.objects.get(username=request.POST['share1'
                            ]):
                        return HttpResponseForbidden()
                    d.users.add(User.objects.get(username=request.POST['share1'
                                ]))
        users = d.users.all()
        users_new = [i.user for i in
                     request.user.friendship.friends.exclude(user__in=d.users.all())]
        variables = RequestContext(request, {'d': d, 'users': users,
                                   'users_new': users_new})
        return render_to_response('devices/device_share.html',
                                  variables)


def netduino(request, id):
    mynetduino = True
    try:
        d1 = request.user.netduino_user.get(pk=id)
    except Netduino.DoesNotExist:
        d1 = None
    if not d1:

        switches = request.user.switch_man_us.all()
        wareabout = request.user.wareabout_us.all()

        for i in switches:

            d1 = i.netduino_switches.get(pk=id)

        for i in wareabout:

            d1 = i.netduino_wareabout.get(pk=id)
        if d1:

            mynetduino = False

    if not d1:
        return HttpResponseForbidden()

    myswitches = None
    mybell = None
    mywareabout = None
    hisswitches = None
    hisbell = None
    hiswareabout = None

    if mynetduino:
        myswitches = d1.switches.all()
        for i in myswitches:
            i.checkshare()
        mybell = d1.bell

        mywareabout = d1.wareabout
        mywareabout.checkshare()
    else:

        mynetduino = False
        hisswitches = d1.switches.filter(users=request.user)
        print hisswitches, 'hissw'
        if d1.wareabout.users.filter(username=request.user.username):
            hiswareabout = d1.wareabout

    if request.method == 'POST':

                             # print request.POST['Remove1']
                           # print 'aaaa',request.GET['rrr'],request.GET['rrr']=='Add Ip'

        if request.POST.get('sw2'):
            s_switches = None
            if mynetduino:
                s_switches = myswitches
            else:
                s_switches = hisswitches
            if request.POST['sw2'] == 'All On':
                for i in s_switches:
                    i.status = True
                res = writeswitches(s_switches, d1)
                if res != 'OK':
                    return HttpResponseForbidden()
                else:
                    s_switches.update(status=True)
            elif request.POST['sw2'] == 'Refresh All':
                print 'r all'
                s_switches = statusswitches(s_switches, d1)
                if s_switches == 'error':
                    return HttpResponseForbidden()
                else:
                    for i in s_switches:
                        SS = Switch_man.objects.filter(pk=i.pk)
                        SS.update(status=i.status)
            elif request.POST['sw2'] == 'All Off':
                for i in s_switches:
                    i.status = False
                res = writeswitches(s_switches, d1)
                if res != 'OK':
                    return HttpResponseForbidden()
                else:
                    s_switches.update(status=False)
            elif request.POST['sw2'] == 'Share':
                return share(request, 'swman', request.POST['sw1'])
            elif request.POST['sw2'] == 'On':
                s_w = s_switches.filter(pk=request.POST['sw1'])
                ll = list()
                ss = s_w[0]
                ss.status = True
                ll.append(ss)
                res = writeswitches(ll, d1)
                if res != 'OK':
                    return HttpResponseForbidden()
                else:
                    s_w.update(status=True)
            elif request.POST['sw2'] == 'Off':
                s_w = s_switches.filter(pk=request.POST['sw1'])
                ss = s_w[0]
                ll = list()
                ss.status = False
                ll.append(ss)
                res = writeswitches(ll, d1)
                if res != 'OK':
                    return HttpResponseForbidden()
                else:
                    s_w.update(status=False)
            if mynetduino:
                myswitches = d1.switches.all()
            else:

                hisswitches = d1.switches.filter(users=request.user)
    d_id = d1.pk

    return render(request, 'devices/netduino.html', {
        'tr': True,
        'N_user': d1.user,
        'd_id': d_id,
        'ip': d1.network_addr,
        'mynetduino': mynetduino,
        'myswitches': myswitches,
        'mywareabout': mywareabout,
        'hisswitches': hisswitches,
        'hiswareabout': hiswareabout,
        'mybell': mybell,
        })


def switch(request, id):

    try:
        d1 = request.user.switch_man_u.get(pk=id)
    except Switch_man.DoesNotExist:
        d1 = None

    try:
        d2 = request.user.switch_man_us.get(pk=id)
    except Switch_man.DoesNotExist:
        d2 = None

    if d1:
        d = d1
    elif d2:
        d = d2
    else:
        d = None
        return HttpResponseForbidden()
    status = ''
    try:
        con = httplib.HTTPConnection(str(d.ip) + ':' + str(d.port),
                timeout=5)
        con.request('GET', '/swman/status/' + str(d.d_pin))
        status = con.getresponse().read()
    except (httplib.HTTPException, socket.error), ex:

        status = 'Connect Your Netduino Error: '  # error.read()
    users_new = None
    if request.method == 'POST':
        if request.POST.get('share2'):
            pass
        else:

            if request.POST['f_request'] == 'Status':
                try:
                    con = httplib.HTTPConnection(str(d.ip) + ':'
                            + str(d.port), timeout=5)
                    con.request('GET', '/swman/status/' + str(d.d_pin))
                    status = con.getresponse().read()
                except (httplib.HTTPException, socket.error), ex:
                    status = 'Connect Your Netduino Error: '  # error.read(
            elif request.POST['f_request'] == 'OFF':
                try:
                    con = httplib.HTTPConnection(str(d.ip) + ':'
                            + str(d.port), timeout=5)
                    con.request('GET', '/swman/off/' + str(d.d_pin))
                    status = con.getresponse().read()
                except (httplib.HTTPException, socket.error), ex:

                    status = 'Connect Your Netduino Error: '  # error.read(
            elif request.POST['f_request'] == 'ON':

                try:
                    con = httplib.HTTPConnection(str(d.ip) + ':'
                            + str(d.port), timeout=5)
                    con.request('GET', '/swman/on/' + str(d.d_pin))
                    status = con.getresponse().read()
                except (httplib.HTTPException, socket.error), ex:
                    status = 'Connect Your Netduino Error: '  # error.read(

    state = True
    if status == 'ON':
        state = False
    users = d.users.all()
    user = d.user

    return render(request, 'devices/switch.html', {
        'd': d,
        'users': users,
        'user1': user,
        'status': status,
        'state': state,
        })


def wareabout(request, id, nid):

    try:
        d1 = request.user.wareabout_u.get(pk=id)
    except Wareabout.DoesNotExist:
        d1 = None

    try:
        d2 = request.user.wareabout_us.get(pk=id)
    except Wareabout.DoesNotExist:
        d2 = None

    if d1:
        d = d1
    elif d2:
        d = d2
    else:
        d = None
        return HttpResponseForbidden()

    macs = None
    mac_send = ''
    n = Netduino.objects.get(pk=nid)
    status = None
    macs = d.bt_addrs.all()
    if request.method == 'POST':
        if request.POST.get('check1'):
            if request.POST['check2'] == 'Check':
                mac = d.bt_addrs.get(pk=request.POST['check1'])
                macs = list()
                macs.append(mac)
                macs = checkmacs(macs, n)
                if macs != 'error':
                    status = 'Connected'
                    for i in macs:
                        d.bt_addrs.filter(pk=i.pk).update(status=i.status)
                    macs = d.bt_addrs.all()
                else:
                    return HttpResponseForbidden()
        elif request.POST.get('check2'):
            if request.POST['check2'] == 'Check All':
                macs = checkmacs(macs, n)
                if macs != 'error':
                    status = 'COnnected'
                    for i in macs:
                        d.bt_addrs.filter(pk=i.pk).update(status=i.status)
                    macs = d.bt_addrs.all()
                else:
                    return HttpResponseForbidden()
    return render(request, 'devices/wareabout.html', {
        'macs': macs,
        'user1': request.user,
        'd': d,
        'status': status,
        'n': netduino,
        })


def bell(request, id):
    try:
        d1 = request.user.door_bell_u.get(pk=id)
    except Door_bell.DoesNotExist:
        d1 = None
    try:
        d2 = request.user.door_bell_us.get(pk=id)
    except Door_bell.DoesNotExist:
        d2 = None
    if d1:
        d = d1
    elif d2:
        d = d2
    else:
        d = None
        return HttpResponseForbidden()
    status = False
    if request.method == 'POST':
        if request.POST.get('Remove2'):
            if request.POST['Remove2'] == 'Remove User':
                d.users.remove(d.users.get(username=request.POST['Remove1'
                               ]))
            elif request.POST['Remove2'] == 'Remove Mac':
                d.bt_addrs.remove(request.user.bt_addr_set.get(pk=request.POST['Remove1'
                                  ]))
            elif request.POST['Remove2'] == 'Remove IP':
                d.network_addrs.remove(request.user.bt_addr_set.get(pk=request.POST['Remove1'
                        ]))
        else:
            if request.POST['f_request'] == 'Status':
                status = False
            elif request.POST['f_request'] == 'OFF':

                status = True
            elif request.POST['f_request'] == 'ON':
                status = False
    bt_a = d.bt_addrs.all()
    net_a = d.network_addrs.all()
    users = d.users.all()
    user = d.user  # print users,'sssssdsssssssssssssss'
    return render(request, 'devices/bell.html', {
        'd': d,
        'users': users,
        'user': user,
        'status': status,
        'bt_a': bt_a,
        'net_a': net_a,
        })


def macsandips(request):
    bt_a = request.user.bt_addr_set.all()
    net_a = request.user.network_addr_set.all()
    switches = request.user.switch_man_u.all()
    wareabouts = request.user.wareabout_u.all()
    bells = request.user.door_bell_u.all()
    if request.POST:
        print 'as'
        if request.POST.get('remmac'):
            request.user.bt_addr_set.get(pk=request.POST['remove'
                    ]).delete()
        elif request.POST.get('remip'):
            request.user.network_addr_set.get(pk=request.POST['remove'
                    ]).delete()
        elif request.POST.get('remswitch', ''):
            request.user.switch_man_u.get(pk=request.POST['remove'
                    ]).delete()
        elif request.POST.get('remwareabout'):
            request.user.wareabout_u.get(pk=request.POST['remove'
                    ]).delete()
        elif request.POST.get('rembell'):
            request.user.door_bell_u.get(pk=request.POST['remove'
                    ]).delete()

    bt_a = request.user.bt_addr_set.all()
    net_a = request.user.network_addr_set.all()

    return render(request, 'devices/macsandips.html', {
        'bt_a': bt_a,
        'net_a': net_a,
        'switches': switches,
        'wareabouts': wareabouts,
        'bells': bells,
        })
