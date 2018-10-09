#!/usr/bin/python
# -*- coding: utf-8 -*-
import re
from django.contrib.auth.models import User
from django.core.exceptions import ObjectDoesNotExist
from django import forms
from django.forms import ModelForm
from models import *
__author__ = 'Attaullah Khan'


class DeviceForm(forms.Form):

    ip = forms.GenericIPAddressField(label='Device IP')
    port = forms.IntegerField(label='Network Port')
    username = forms.CharField(label='Last Name', max_length=30)
    d_name = forms.CharField(label='Device Name', max_length=30)
    email = forms.EmailField(label='Email')
    password1 = forms.CharField(label='Password',
                                widget=forms.PasswordInput())

    password2 = forms.CharField(label='Password (Again)',
                                widget=forms.PasswordInput())

    def clean_password2(self):

        if 'password1' in self.cleaned_data:
            password1 = self.cleaned_data['password1']
            password2 = self.cleaned_data['password2']
        if password1 == password2:
            return password2
        raise forms.ValidationError('Passwords do not match.')

    def clean_d_name(self):
        d_name = self.cleaned_data['Device Name']
        try:
            Device.objects.get(d_name=d_name)
        except ObjectDoesNotExist:
            return username
        raise forms.ValidationError('Device name already taken.')


class SwitchForm(DeviceForm):
    d_pin = forms.IntegerField(label='Switch Pin')


class Switch_sen_f(DeviceForm):

    d_pin = forms.IntegerField(label='Switch Pin')
    s_pin = forms.IntegerField(label='Sensor Pin')

class Door_bell_f(ModelForm):
    class Meta:

        model = Door_bell
        fields = ('d_name', 'bt_addrs', 'network_addrs')
        labels = {'d_name': 'Device Name', 'Bt_addrs': 'Blutooth Macs',
                  'network_addrs': 'Ips'}

    def __init__(self, *args, **kwargs):
        self.user = kwargs.pop('user')
        super(Door_bell_f, self).__init__(*args, **kwargs)
        self.fields['bt_addrs'].widget = \
            forms.widgets.CheckboxSelectMultiple()
        self.fields['network_addrs'].widget = \
            forms.widgets.CheckboxSelectMultiple()


        self.fields['bt_addrs'].queryset = self.user.bt_addr_set.all()

        self.fields['network_addrs'].queryset = \
            self.user.network_addr_set.all()

class Wareabout_f(ModelForm):

    class Meta:

        model = Wareabout
        fields = ('d_name', 'bt_addrs', 'network_addrs')
        labels = {'d_name': 'Device Name'}

    def __init__(self, *args, **kwargs):
        self.user = kwargs.pop('user')
        super(Wareabout_f, self).__init__(*args, **kwargs)
        self.fields['bt_addrs'].widget = \
            forms.widgets.CheckboxSelectMultiple()
        self.fields['network_addrs'].widget = \
            forms.widgets.CheckboxSelectMultiple()
        self.fields['bt_addrs'].queryset = self.user.bt_addr_set.all()

        self.fields['network_addrs'].queryset = \
            self.user.network_addr_set.all()


class Bt_addr_f(ModelForm):

    class Meta:

        model = Bt_addr
        fields = ('name', 'mac_addr', 'code')
        labels = {'name': 'Person Name', 'mac_addr': 'Mac Address',
                  'code': 'Pair Code'}

    def __init__(self, *args, **kwargs):

        super(Bt_addr_f, self).__init__(*args, **kwargs)

class Network_addr_f(ModelForm):

    class Meta:

        model = Network_addr
        fields = ('ip', 'port', 'username', 'password', 'name')
        labels = {
            'ip': 'IP Address',
            'port': 'Port',
            'username': 'Username',
            'password': 'Password',
            'name': 'Name',
            }

    def __init__(self, *args, **kwargs):
        super(Network_addr_f, self).__init__(*args, **kwargs)
        self.fields['password'].widget = forms.widgets.PasswordInput()


class Switch_man_f(ModelForm):
    class Meta:
        model = Switch_man
        fields = ('d_name', 'd_pin')
        labels = {'d_name': 'Device Name', 'd_pin': 'Device Pin'}

    def __init__(self, *args, **kwargs):
        super(Switch_man_f, self).__init__(*args, **kwargs)


class Netduino_f(ModelForm):

    class Meta:

        model = Netduino
        fields = ('network_addr', 'switches', 'bell', 'wareabout')
        labels = {
            'd_name': 'Device Name',
            'network_addr': 'Ip',
            'swithes': 'Select Switches',
            'bell': 'Select Door Bell',
            }

        def __init__(self, *args, **kwargs):

            super(Netduino_f, self).__init__(*args, **kwargs)
            self.fields['network_addrs'].widget = \
                forms.widgets.CheckboxChoiceInput()

            self.fields['switches'].widget = \
                forms.widgets.CheckboxChoiceInput()

            self.fields['bell'].widget = \
                forms.widgets.CheckboxChoiceInput()

            self.fields['wareabout'].widget = \
                forms.widgets.CheckboxChoiceInput()

            self.fields['network_addrs'].queryset = \
                self.user.network_addr_set.all()
            self.fields['switches'].queryset = \
                self.user.switch_man_u.all()
            self.fields['bell'].queryset = self.user.door_bell_u.all()

            self.fields['wareabout'].queryset = \
                self.user.door_bell_u.all()
            self.fields['bell'].queryset = self.user.wareabout_u.all()

