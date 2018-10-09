#!/usr/bin/python
# -*- coding: utf-8 -*-

from django.http import HttpResponseRedirect, HttpResponseForbidden
from devices.models import *

 # Create your views here.

from devices.switch_f import *
from django.template import RequestContext
from django.shortcuts import render_to_response
import urllib2
import httplib
import socket
from devices.views import *
__author__ = 'Attaullah Khan'


def checkmacs(macs, netduino):
    print macs
    req = ''
    for i in macs:
        req = req + str(i.mac_addr) + '%'
    req = req[0:len(req) - 1]
    print req, 'request'

    res = netduino_req(unicode(netduino.network_addr.ip) + ':'
                       + unicode(netduino.network_addr.port),
                       '/checkmacs/', req)
    print res, 'respons'
    if len(res.split('%')) == len(macs):
        print 'hahahahah'
        j = 0
        for i in res.split('%'):
            i = i.split('?')

            macs[j].status = i[1]
            j = j + 1
        return macs
    else:
        return 'error'


def statusswitches(switches, netduino):
    req = ''
    print switches
    for i in switches:
        req = req + str(i.d_pin) + '%'
    req = req[0:len(req) - 1]
    res = netduino_req(unicode(netduino.network_addr.ip) + ':'
                       + unicode(netduino.network_addr.port),
                       '/statusswitches/', req)
    print res, 'respons'
    if len(res.split('%')) == len(switches):
        print 'hahahahah'
        j = 0
        for i in res.split('%'):
            i = i.split('?')

            switches[j].status = i[1]
            j = j + 1
            return switches
    else:
        return 'error'


def writeswitches(switches, netduino):
    req = ''
    print switches[0].status, 'kkkkkkkkkkkkkk'
    for i in switches:
        req = req + str(i.d_pin) + '?' + str(i.status) + '%'
    req = req[0:len(req) - 1]
    print req, 'request'

    res = netduino_req(unicode(netduino.network_addr.ip) + ':'
                       + unicode(netduino.network_addr.port),
                       '/writeswitches/', req)
    print res, 'respons'
    if len(res.split('%')) == len(switches):
        print 'hahahahah'
        j = 0
        for i in res.split('%'):
            i = i.split('?')
            switches[j].status = i[1]
            j = j + 1
            for i in switches:
                i.save()
        return 'OK'
    else:
        return 'error'


def netduino_req(
    ip,
    uripart,
    data=None,
    t_out=None,
    ):
    uri = ip
    print ip, 'asdddddddddddddddddddddddddddd'
    headers = {'Content-type': 'application/x-www-form-urlencoded',
               'Accept': 'text/plain'}
    try:
        if t_out:
            con = httplib.HTTPConnection(uri, t_out)
        else:
            con = httplib.HTTPConnection(uri)
        if data:
            con.request('POST', uripart, 'POST__DATA__START' + data
                        + 'POST__DATA__END', headers)
        else:
            con.request('GET', uripart)
        r = con.getresponse()
        return r.read()
    except (httplib.HTTPException, socket.error), ex:

        print ex
        return 'error'

def textbetween(s, substring1, substring2):
    return s[s.index(substring1) + len(substring1):s.index(substring2)]

def createdevices(user, netduino):
    req = 'create_devices_start Switches_start'
    switches = netduino.switches.all()
    for i in switches:
        i.status = False
        req = req + str(i.d_name) + '?' + str(i.d_pin) + '?' \
            + str(i.status) + '%'
    req = req[0:len(req) - 1] \
        + 'Switches_end  Bell_start network_addrs_start'
    bell = netduino.bell
    nets = bell.network_addrs.all()
    for i in nets:
        req = req + str(i.ip) + '?' + str(i.port) + '?' + str(i.name) \
            + '%'
    req = req[0:len(req) - 1] + 'network_addrs_end macs_start'
    macs = bell.bt_addrs.all()
    for i in macs:
        req = req + str(i.mac_addr) + '?' + str(i.name) + '?Mac%'
    req = req[0:len(req) - 1] + 'macs_end Bell_end create_devices_end'
    res = netduino_req(unicode(netduino.network_addr.ip) + ':'
                       + unicode(netduino.network_addr.port),
                       '/createdevices/', req)
    if res == 'OK':
        return 'OK'
    else:
        return 'error'
    if res == 'OK':
        res = textbetween(res, 'get_all_devices_start',
                          'get_all_devices_end')
        switches = textbetween(res, 'Switches_start', 'Switches_end')
        bell = textbetween(res, 'Bell_start', 'Bell_end')

def getalldevices(request, netduino):
    netduino.user = request.user
    res = netduino_req(unicode(netduino.network_addr.ip) + ':'
                       + unicode(netduino.network_addr.port),
                       '/getalldevices/')
    if res == 'OK':
        return 'error'
    else:
        res = textbetween(res, 'get_all_devices_start',
                          'get_all_devices_end')
        switches = textbetween(res, 'Switches_start', 'Switches_end')
        bell = textbetween(res, 'Bell_start', 'Bell_end')
        nets = textbetween(bell, 'network_addrs_start',
                           'network_addrs_end')
        macs = textbetween(bell, 'macs_start', 'macs_end')
        splitarray = switches.split('%')
        userswitches = request.user.switch_man_u.all()
        swlist = list()
        bell_obj = request.user.door_bell_u.filter(d_type='bell',
                d_name='Netduino_bell'
                + unicode(netduino.network_addr.ip) + ':'
                + unicode(netduino.network_addr.port),
                user=request.user)
        if bell_obj:
            bell_obj = bell_obj[0]
        else:
            bell_obj = Door_bell(d_type='bell', d_name='Netduino_bell'
                                 + unicode(netduino.network_addr.ip)
                                 + ':'
                                 + unicode(netduino.network_addr.port),
                                 user=request.user)
        bell_obj.save()
        netduino.bell = bell_obj
        for i in macs.split('%'):
            i = i.split('?')
            mac = request.user.bt_addr_set.filter(mac_addr=i[0],
                    name=i[1])
            if mac:
                mac = mac[0]
            else:
                mac = Bt_addr(mac_addr=i[0], name=i[1],
                              user=request.user)
            mac.save()
            netduino.bell.bt_addrs.add(mac)
        for i in nets.split('%'):
            i = i.split('?')
            net = request.user.network_addr_set.filter(ip=i[0],
                    port=i[1], name=i[2])
            if net:
                net = net[0]
            else:
                net = Network_addr(ip=i[0], port=i[1], name=i[2],
                                   user=request.user)
            net.save()
            print net
            netduino.bell.network_addrs.add(net)
        a = request.user.wareabout_u.filter(d_type='wareabout',
                d_name='netduino_ware_about')
        if a:
            a = a[0]
        else:
            a = Wareabout(d_name='netduino_ware_about',
                          user=request.user)
        a.save()
        netduino.wareabout = a
        netduino.save()
        for i in splitarray:  # switches
            i = i.split('?')
            sw = request.user.switch_man_u.filter(d_name=i[0],
                    d_pin=i[1])
            if sw:
                sw = sw[0]
                sw.status = i[2]
            else:
                sw = Switch_man(d_type='swman', d_name=i[0],
                                d_pin=i[1], status=i[2],
                                user=request.user)
            sw.save()
            print sw, 'sssssssss'
            netduino.switches.add(sw)
        return 'OK'
