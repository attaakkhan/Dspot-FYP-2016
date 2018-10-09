#!/usr/bin/python
# -*- coding: utf-8 -*-
from django.shortcuts import render

from django.template import RequestContext

# Create your views here.

from django.http import HttpResponse
from django.template import Context
from django.template.loader import get_template
from django.http import HttpResponse, Http404
from django.contrib.auth.models import User
from django.middleware.csrf import *
from django.http import HttpResponseRedirect
from django.contrib.auth import logout
from django.shortcuts import render_to_response
from bookm.forms import *
from django.shortcuts import get_object_or_404
from friends.models import *

from django.contrib.auth.decorators import login_required
from itertools import chain


# @csrf_protect()

def f_request(r_user, user):
    if Friendship.objects.are_friends(r_user, user):
        raise RuntimeError('%r amd %r are already friends' % (r_user,
                           user))
    try:
                  # If there's a friendship request from the other user accept it.
        get_object_or_404(FriendshipRequest, from_user=user,
                          to_user=r_user).accept()
    except Http404:

        request_message = 'message'

                                     # If we already have an active friendship request IntegrityError
                                      # will be raised and the transaction will be rolled back.

        FriendshipRequest.objects.create(from_user=r_user,
                to_user=user, message=request_message)


@login_required(login_url='/login/')
def friends_page(request, username):
    user = get_object_or_404(User, username=username)
    friends = [friendship.to_friend for friendship in
               user.friend_set.all()]
    friend_bookmarks = \
        Bookmark.objects.filter(user__in=friends).order_by('-id')
    return render(request, 'friends_page.html', {
        'username': username,
        'friends': friends,
        'bookmarks': friend_bookmarks[:10],
        'show_tags': True,
        'show_user': True,
        })


def profile(request, username):
    user = get_object_or_404(User, username=username)
    f_button = ''
    a_button = 'Hidden'
    print 'Fucku 1111111222'
    if request.method == 'POST':
        if 'f_request1' in request.POST:

            if request.POST['f_request1'] == 'Friend':
                Friendship.objects.unfriend(request.user, user)
            elif request.POST['f_request1'] == 'Request Sent':

                get_object_or_404(FriendshipRequest,
                                  from_user=request.user,
                                  to_user=user).cancel()
            elif request.POST['f_request1'] == 'Send':

                f_request(request.user, user)
            elif request.POST['f_request1'] == 'Accept':
                get_object_or_404(FriendshipRequest, from_user=user,
                                  to_user=request.user).accept()
        elif 'f_request2' in request.POST:

            if request.POST['f_request2'] == 'Decline':
                get_object_or_404(FriendshipRequest, from_user=user,
                                  to_user=request.user).decline()
    if user == request.user:
        f_button = 'Me'
    elif request.user.friendship.friends.filter(user=user):
        f_button = 'Friend'
    elif FriendshipRequest.objects.filter(to_user=user,
            from_user=request.user):

        f_button = 'Request Sent'
    elif FriendshipRequest.objects.filter(to_user=request.user,
            from_user=user):
        f_button = 'Accept'  # decline will appear on front end
        a_button = 'submit'
    else:
        f_button = 'Send'
    share_with_you = list(chain(
        request.user.switch_man_us.filter(user=request.user),
        request.user.switch_sen_us.filter(user=request.user),
        request.user.switch_tim_us.filter(user=request.user),
        request.user.alarm_clock_us.filter(user=request.user),
        request.user.wareabout_us.filter(user=request.user),
        request.user.door_bell_us.filter(user=request.user),
        ))
    return render(request, 'profile.html', {
        'uf_name': user.first_name,
        'ul_name': user.last_name,
        'u_email': user.email,
        'f_button': f_button,
        'share_with_you': share_with_you,
        'a_button': a_button,
        })


def search_page(request):
    if request.method == 'POST':
        if request.POST['search']:
            f_list = User.objects.filter(username=request.POST['search'
                    ])
            print f_list, request.POST['search']
            d_list = list(chain(
                request.user.switch_man_u.filter(d_name=request.POST['search'
                        ]),
                request.user.switch_sen_u.filter(d_name=request.POST['search'
                        ]),
                request.user.switch_tim_u.filter(d_name=request.POST['search'
                        ]),
                request.user.alarm_clock_u.filter(d_name=request.POST['search'
                        ]),
                request.user.wareabout_u.filter(d_name=request.POST['search'
                        ]),
                request.user.door_bell_u.filter(d_name=request.POST['search'
                        ]),
                ))
            return render(request, 'search.html', {'f_list': f_list,
                          'd_list': d_list})
    return render(request, 'search.html', {})


def logout_page(request):
    logout(request)
    return HttpResponseRedirect('/')


def main_page(request):
    if request.user.is_authenticated():
        return render(request, 'base.html', {})
    else:
        return render(request, 'main_page.html', {})

def user_page(request, username):
    try:
        user = User.objects.get(username=username)
    except:
        raise Http404('Requested user not found.')
    bookmarks = user.bookmark_set.all()

    return render(request, 'user_page.html', {'username': username,
                  'bookmarks': bookmarks})

def friends(request):
    friends = [i.user for i in request.user.friendship.friends.all()]
    return render(request, 'friends.html', {'friends': friends})


def register_page(request):
    if request.method == 'POST':
        form = RegistrationForm(request.POST)
        if form.is_valid():
            user = \
                User.objects.create_user(username=form.cleaned_data['username'
                    ], first_name=form.cleaned_data['firstname'],
                    last_name=form.cleaned_data['lastname'],
                    password=form.cleaned_data['password1'],
                    email=form.cleaned_data['email'])
            return HttpResponseRedirect('/')
    else:
        form = RegistrationForm()

    return render(request, 'registration/register.html', {'form': form})
