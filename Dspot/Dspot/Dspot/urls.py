#!/usr/bin/python
# -*- coding: utf-8 -*-
"""django_bookmarks URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
   https://docs.djangoproject.com/en/1.9/topics/http/urls/
Examples:
Function views
   1. Add an import:  from my_app import views
   2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
   1. Add an import:  from other_app.views import Home
   2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
   1. Add an import:  from blog import urls as blog_urls
   2. Import the include() function: from django.conf.urls import url, include
   earch/$', search_page). 'dd a URL to urlpatterns:  url(r'^blog/', include(blog_urls))
    o

"""

import os.path
from friends.views import *

# from friends.views import friendship_request

from django.conf.urls import url
from django.contrib import admin
from bookm.views import *
from devices.views import *
from django.contrib.auth import views
from django.views.static import serve
__author__ = 'Attaullah Khan'

site_media = os.path.join(os.path.dirname(__file__), 'site_media')
urlpatterns = [
    url(r'^admin/', admin.site.urls),
    url(r'^$', main_page),
    url(r'^user/(\w+)/$', user_page),
    url(r'^login/$', views.login),
    url(r'^logout/$', logout_page),
    url(r'^site_media/(?P<path>.*)$', serve,
        {'document_root': site_media}),
    url(r'^register/$', register_page),
    url(r'^friends1/(\w+)/$', friends_page),
    url(r'^do/(\w+)/([0-9]{1})/$', do_friend),
    url(r'^add/(?P<username>[\+\w\.@-_]+)/([0-9]{2})/$', do_friend),
    url(r'^articles/([0-9]{4})/([0-9]{2})/$', do_friend),
    url(r'^search/$', search_page),
    url(r'^profile/(\w+)/$', profile),
    url(r'^addmac/$', add_mac),
    url(r'^addnet/$', add_net),
    url(r'^addbell/$', add_bell),
    url(r'^addswman/$', add_sw_man),
    url(r'^adddevice/$', add_device),
    url(r'^netduino/(\d{1,9})/$', netduino),
    url(r'^editbell/(\d{1,9})/$', add_bell),
    url(r'^editmac/(\d{1,8})/$', add_mac),
    url(r'^editnet/(\d{1,9})/$', add_net),
    url(r'^editswman/(\d{1,9})/$', add_sw_man),
    url(r'^mydevices/$', my_devices),
    url(r'^addnetduino/$', add_netduino),
    url(r'^editnetduino/(\d{1,9})/$', add_netduino),
    url(r'^bell/(\d{1,9})/$', bell),
    url(r'^swman/(\d{1,9})/$', switch),
    url(r'^editwareabout/(\d{1,9})/$', add_wareabout),
    url(r'^addwareabout/$', add_wareabout),
    url(r'^wareabout/(\d{1,9})/(\d{1,9})/$', wareabout),
    url(r'^friends/$', friends),
    url(r'^macsandips/$', macsandips),
    url(r'^share/(\w+)/(\d{1,9})/$', share),
    ]
