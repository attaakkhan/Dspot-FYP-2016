# Socail networking site for IOT devices

One Paragraph of project description goes here

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

1. Ubuntu or any unix based  machine to run this project
2. Python pip
3. Netduino 



### Setting up the enviroment
#### 1. Install and configure PostgreSQL on Debian/Ubuntu

Update your system to the latest available version in the repository
```
$  sudo apt-get update
```
 Install PostgreSQL using apt-get
```
$  sudo apt-get install postgresql postgresql-contrib
```
Creat user
```
$  sudo -u postgres createuser --interactive --pwprompt
```
```
Output:
Enter name of role to add: dspot
Enter password for new role:dspot 
Enter it again: dspot
Shall the new role be a superuser? (y/n) y
```
Create Database under dspot user

```
$  sudo -u postgres createdb -O dspot dspot_db
```
#### 2. Install and Django on Debian/Ubuntu

Install django using pip
```
$ sudo pip install django
```
Install django module for postgresql
```
$  pip install psycopg2
```


## Building and Running
### 1. Building and Running--The main django server

To clone this project use git
```
$  git clone https://github.com/attaakkhan/Dspot-FYP-2016.git

```
Navigate to dspot directory.
```
$ cd Dspot-FYP-2016/Dspot/Dspot
```
Now migrate the changes to Postresql
```
$ python manage.py migrate
```
Run The Server
```
$ python manage.py runserver
```
Now the server is running open http://127.0.0.1:8000/register/ in your Web Browser you will see the site
### 2. Building and Running--The Netuidno Micro server 
### 3. Building and Running--The Android APP
