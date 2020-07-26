# Wordpress-utils
Utilities for Wordpress

Project written in PHP based on [CodeIgniter](https://codeigniter.com).

## Start project

### Local Development Server
CodeIgniter 4 comes with a local development server. 
You can use the serve script to launch it, with the following command line in the main directory:

`php spark serve`

This will launch the server and you can now view your application in your browser at `http://localhost:8080`.
It should NEVER be used on a production server.

The local development server can be customized with three command line options:
* `--host` to specify a different host. Example: `php spark serve --host=example.dev`
* `--port` to specify a different port. Example: `php spark serve --port=8081`
* `--php` to the path of a particular PHP version you want to use. Example: `php spark serve --php=/usr/bin/php7.6.5.4`


## Setup

### Requirements 
To setup this project you need:
* [PHP 7 or later](https://www.php.net/downloads.php)
* [Composer](https://getcomposer.org/)
* [VirtualBox v6 or later](https://www.virtualbox.org/wiki/Downloads)


### PHP configuration

**Base configuration and extensions**

* Use `php.ini-development` as `php.ini`
* Edit your PHP configuration
  * Set the extensions' directory: uncomment the following line `extension_dir = "ext"`
  * Uncomment CodeIgniter required extensions
    * `extension=curl`
    * `extension=ftp`
    * `extension=fileinfo`
    * `extension=gd2`
    * `extension=intl`
    * `extension=mbstring`
    * `extension=mysqli`  (because Wordpress uses MySQL by default)
    * `extension=openssl`

**Configure path** 

Add PHP exec and Composer to your path:

* Windows
  * `PHP_HOME`
  * `PHP_COMPOSER`
  * `PATH=%PATH%;%PHP_HOME%;%PHP_COMPOSER%`
* Linux
  * `vim /etc/bashrc`
  * Add

To check the result: `php -v`

### Composer setup

Create a folder to host your development. 

`composer create-project codeigniter4/appstarter project-root`

### Git ignore

Key points: you should exclude 'version' folder from the Git.


## Project structure

The project structure is based on CodeIgniter project generation. Folders are:
* `app, public, tests, writable`
* `vendor/codeigniter4/framework/system`
* `vendor/codeigniter4/framework/app & public` (compare with yours after updating)


## Configuration 

### Settings file(s)

**Apache2 configuration style: multi-files**

Like any composer application, you can use a *multiple files* configuration. Each file describes each setting with a lot of details:
* `app/Config/App.php` to set base URL
* `app/Config/Database.php` to set the database configuration


**Spring-Boot configuration style: single file**

Instead, if you prefer to collect all the settings together in 1 file, you can use the `env` file.
* `app.baseURL="http://example.com"` 
* database configuration


### Security 

**Environment**

One additional measure to take in production environments is to disable PHP error reporting and any other development-only functionality. 
This can be done by setting the `ENVIRONMENT` constant, which is more fully described on the environments page. 
By default, the application will run using the "`production`" environment. 
To take advantage of the debugging tools provided, you should set the environment to "`development`".


**Authorization**

If you will be running your site using a web server (e.g. Apache or Nginx), 
you will need to modify the permissions for the writable folder inside your project, 
so that it is writable by the user or account used by your web server. 
`chown -R www-data:www-data $projectDir`



## Documentation

* This project is based on [CodeIgniter user guide](https://codeigniter.com/user_guide/)

