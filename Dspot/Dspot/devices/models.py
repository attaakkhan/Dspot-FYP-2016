#!/usr/bin/python
# -*- coding: utf-8 -*-



 
 
from __future__ import unicode_literals

from django.db import models

from django.contrib.auth.models import User


# from  models import User
# Create your models here.
__author__      = "Attaullah Khan"
class Device(models.Model):

    d_name = models.CharField(max_length=50)
    d_type = models.CharField(max_length=50)

    shared = models.BooleanField(default=False)
    status = models.BooleanField(default=False)
    class Meta:
        abstract = True
    def __unicode__(self):
        return u'%s ' % self.d_name
    def share(self, user):
        self.users.add(user)
    def unshare(user):
        self.users.remove(self.users.all())
    def checkshare(self):
        if self.users.all():
            shared = True
        else:
            shared = False
        self.save()


class Switch_man(Device):

    users = models.ManyToManyField(User, related_name='switch_man_us')
    user = models.ForeignKey(User, null=True,
                             related_name='switch_man_u')
    d_pin = models.IntegerField()
    def status1():
        return False

    def write(vlaue):
        return False

    def __unicode__(self):
        return u'%s(pin %s)' % (self.d_name, self.d_pin)


class Switch_sen(Device):

    user = models.ForeignKey(User, related_name='switch_sen_u')
    users = models.ManyToManyField(User, related_name='switch_sen_us')
    d_pin = models.IntegerField()
    s_pin = models.IntegerField()

    def __unicode__(self):
        return u'%s(%s:%s):%s     ' % (
            self.d_name,
            self.d_type,
            self.ip,
            self.port,
            self.d_pin,
            user.username,
            )


class Switch_tim(Device):

    user = models.ForeignKey(User, related_name='switch_tim_u')
    users = models.ManyToManyField(User, related_name='switch_tim_us')
    d_pin = models.IntegerField()

    def __unicode__(self):
        return u'%s with  %s:%s:%s Type %s   %s    ' % (self.d_name,
                self.d_type, self.ip, self.port, self.self._pin)


class Alarm_clock(Device):

    user = models.ForeignKey(User, related_name='alarm_clock_u')
    users = models.ManyToManyField(User, related_name='alarm_clock_us')
    d_pin = models.IntegerField()

    def __unicode__(self):
        return u'%s with  %s:%s:%s Type %s   %s    ' % (
            self.d_name,
            self.d_type,
            self.ip,
            self.port,
            self.d_pin,
            user.username,
            )


class Network_addr(models.Model):

    user = models.ForeignKey(User, null=True)
    name = models.CharField(max_length=50, null=True)  # make it unique
    ip = models.GenericIPAddressField()
    port = models.IntegerField()
    username = models.CharField(max_length=30)
    password = models.CharField(max_length=50)

    def __unicode__(self):
        return u'%s(%s:%s)' % (self.name, self.ip, self.port)


class Bt_addr(models.Model):

    user = models.ForeignKey(User, null=True)
    name = models.CharField(max_length=50)
    mac_addr = models.CharField(max_length=50)
    code = models.IntegerField(default=1234)
    status = models.CharField(max_length=100, null=True)

    def __unicode__(self):
        return u' %s(%s) ' % (self.name, self.mac_addr)


class Door_bell(Device):

    user = models.ForeignKey(User, null=True, related_name='door_bell_u'
                             )
    users = models.ManyToManyField(User, related_name='door_bell_us')

      # d_pin=models.IntegerField()
    bt_addrs = models.ManyToManyField(Bt_addr,
            related_name='door_bt_set')
    network_addrs = models.ManyToManyField(Network_addr,
            related_name='door_network_set')

    def __unicode__(self):
        return u'%s ' % self.d_name


class Wareabout(Device):

    user = models.ForeignKey(User, null=True, related_name='wareabout_u'
                             )
    users = models.ManyToManyField(User, related_name='wareabout_us')
    network_addrs = models.ManyToManyField(Network_addr,
            related_name='ware_network_set')
    bt_addrs = models.ManyToManyField(Bt_addr,
            related_name='ware_bt_set')

    def __unicode__(self):
        return u'%s:' % self.d_name


class Netduino(models.Model):

    d_name = d_name = models.CharField(default='netduino',
            max_length=50)
    user = models.ForeignKey(User, null=True,
                             related_name='netduino_user')
    switches = models.ManyToManyField(Switch_man, null=True,
            related_name='netduino_switches')
    bell = models.ForeignKey(Door_bell, null=True,
                             related_name='netduino_bell')
    wareabout = models.ForeignKey(Wareabout, null=True,
                                  related_name='netduino_wareabout')

              # d_pin=models.IntegerField()
                   # bt_addrs=models.ForeignKey(User,related_name='netduino_user')
                    # models.ManyToManyField(Bt_addr,related_name='door_bt_set')

    network_addr = models.ForeignKey(Network_addr, null=True,
            related_name='Netduino_ip')

    def __unicode__(self):
        return u'%s  ' % self.d_name
