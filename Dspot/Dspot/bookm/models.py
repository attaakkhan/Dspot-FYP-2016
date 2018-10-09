from __future__ import unicode_literals

from django.db import models
from django.contrib.auth.models import User
# Create your models here
from django.db import models
from django.db import models
class Link(models.Model):
 url = models.URLField(unique=True)
class Bookmark(models.Model):
 title = models.CharField(max_length=200)
 user = models.ForeignKey(User)
 link = models.ForeignKey(Link)
class Friendship(models.Model):
    from_friend = models.ForeignKey(
            User, related_name='friend_set'
            )
    to_friend = models.ForeignKey(
            User, related_name='to_friend_set'
            )
    def __str__(self):
        return '%s, %s' % (
                self.from_friend.username,
                self.to_friend.username
                )
        class Admin:
            pass
        class Meta:
            unique_together = (('to_friend', 'from_friend'), )
