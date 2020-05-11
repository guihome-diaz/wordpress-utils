# wordpress-utils
Utilities for Wordpress

Project written in PHP based on [Laravel framework](https://laravel.com/).

## Setup

### Requirements 
To setup this project you need:
* [PHP 7 or later](https://www.php.net/downloads.php)
* [Composer](https://getcomposer.org/)
* [Laravel](https://laravel.com/docs/7.x/installation)
* [Vagrant](https://www.vagrantup.com/downloads.html)
* [VirtualBox v6 or later](https://www.virtualbox.org/wiki/Downloads)

Add PHP exec and Composer to your path:
* `PHP_HOME`
* `PHP_COMPOSER`
* `PATH=%PATH%;%PHP_HOME%;%PHP_COMPOSER%`

Configure PHP as follow:
* Use `php.ini-development` as `php.ini`
* Edit your PHP configuration
  * Set the extensions directory: uncomment the following line `extension_dir = "ext"`
  * Uncomment LARAVEL required extensions
    * `extension=ftp`
    * `extension=fileinfo`
    * `extension=gd2`
    * `extension=mbstring`
    * `extension=openssl`


### Laravel Homestead 

Once the environment is ready, install *[Laravel Homestead](https://laravel.com/docs/7.x/homestead)*

