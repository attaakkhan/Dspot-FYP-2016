from django.http import HttpResponseBadRequest, Http404,HttpResponse
from django.db import transaction
from django.views.generic.base import RedirectView
from django.shortcuts import get_object_or_404
from django.utils.translation import ugettext
from django.contrib.auth.models import User
from django.contrib.auth.decorators import login_required
from models import FriendshipRequest, Friendship
from app_settings import REDIRECT_FALLBACK_TO_PROFILE
from itertools import chain
@login_required


def do_friend(request,username,arg3):
    #return HttpResponse(username)
    if request.user.username == username:
         return HttpResponseBadRequest(ugettext(u'Both user are the same!!!!!!.'), )
    user = get_object_or_404(User, username=username)
    #return HttpResponse(username)

    if arg3=='1':
        f_accept(request.user,user)
        return HttpResponse('accepted')
    if arg3=='2':
        f_decline(request.user,user)
        return HttpResponse('declned')
    if arg3=='3': 
        f_cancel(request.user, user)
        return HttpResponse('cancelled')
    if arg3=='4': 
        f_request(request.user,user)
        return HttpResponse('requested')
    if arg3=='5':
        f_block(request.user,user)
        return HttpResponse('blocked')
    if arg3=='6':
        f_unblock(request.user,user)
        return HttpResponse('unblocked')
#@login_required
#@transaction.atomic
def f_accept(r_user, from_user):
        #print 'aaaaaaaaaaaaaaaaaaaaaaaaaaa'
        get_object_or_404(
            FriendshipRequest,
            from_user=from_user,
            to_user=r_user).accept()

#@login_required
@transaction.atomic
def f_request(r_user, user):
        if Friendship.objects.are_friends(r_user, user):
            raise RuntimeError(
                '%r amd %r are already friends' % (r_user, user),
            )
        try:
            # If there's a friendship request from the other user accept it.
            f_accept(r_user,user)
        except Http404:
            request_message = 'message'
            # If we already have an active friendship request IntegrityError
            # will be raised and the transaction will be rolled back.
            FriendshipRequest.objects.create(
                from_user=r_user,
                to_user=user,
                message=request_message,)
@login_required
def f_decline(r_user, user):
        get_object_or_404(FriendshipRequest,
                          from_user=user,to_user=request.user).decline()
@login_required
def f_cancel(r_user, user):
        get_object_or_404(FriendshipRequest,
                          from_user=r_user,
                          to_user=user).cancel()
@login_required
def f_unfriend(r_user, user):
        Friendship.objects.unfriend(r_user, user)
@login_required
def f_block(r_user, user, **kwargs):
        request.user.user_blocks.blocks.add(user)
@login_required
def f_unblock(r_user, user):
        r_user.user_blocks.blocks.remove(user)


#friendship_request = login_required(FriendshipRequestView.as_view())
#friendship_accept = login_required(FriendshipAcceptView.as_view())
#friendship_decline = login_required(FriendshipDeclineView.as_view())
#friendship_cancel = login_required(FriendshipCancelView.as_view())
#friendship_delete = login_required(FriendshipDeleteView.as_view())
#user_block = login_required(UserBlockView.as_view())
#user_unblock = login_required(UserUnblockView.as_view())
