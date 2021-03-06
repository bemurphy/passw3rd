------------------------------------------------------------------------------
Command line use
 
    Generate key/iv in ~/ by default
 
        $ passw3rd -g
        generated keys in /Users/user
 
        $ passw3rd -g ~/Desktop/
        generated keys in /Users/user/Desktop/
 
    Create a password file
 
        $ passw3rd -e foobar_app
        Enter the password: 
        Wrote password to /Users/neilmatatall/foobar_app
        $ passw3rd -e foobar_app -p ~/Desktop/
        Enter the password: 
        Wrote password to /Users/neilmatatall/Desktop/foobar_app
 
    Read a password file
 
        $ passw3rd -d foobar_app
        The password is: asdf
        $ passw3rd -d foobar_app -p ~/Desktop/
        The password is: asdf
 
 
------------------------------------------------------------------------------
Manual JDBC Connection
 
    Before:
 
    Datasource ds = new Datasource();
    ds.setPassword("suparSekret");
 
    After:
 
    Datasource ds = new Datasource();
    ds.setPassword(PasswordService.getPassword(USER);
 
------------------------------------------------------------------------------
Java properties file
 
    Before:
 
    password=suparSekret
 
    After:
 
    password=${password}
 
------------------------------------------------------------------------------
Ruby on Rails config/database.yml
 
    initializer:
    Passw3rd::PasswordService.password_file_dir = "passwords"
 
    Before:
 
    development:
      adapter: mysql
      database: rails_development
      username: root
      password: my super secret password
 
 
    After:
 
    development:
      adapter: mysql
      database: rails_development
      username: root
      password: <%= PasswordService.get_password('foobar_app') -%>
 
------------------------------------------------------------------------------
OpenSSL command line
 
    $ openssl enc -e -aes-256-cbc -K `cat ~/.passw3rd-encryptionKey`  -iv `cat ~/.passw3rd-encryptionIV` -in README.md -out test.out
    $ openssl enc -d -aes-256-cbc -K `cat ~/.passw3rd-encryptionKey`  -iv `cat ~/.passw3rd-encryptionIV` -out README.md -in test.out