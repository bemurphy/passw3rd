![Build status](/https://secure.travis-ci.org/oreoshake/passw3rd.png)

------------------------------------------------------------------------------
Introduction
------------------------------------------------------------------------------

This is a collection of encryption libraries intended to encrypt and store
passwords outside of source code.

Some advantages of keeping credentials out of source code are:

1. Credentials are not passed around when source code is shared.
2. Unintentional exposure of source code does not reveal credentials.
3. Read-access to source code can be much more permissive.
4. Source code can be checked into version control systems without concern
   for exposure of credentials.
5. It is easier to change credentials without having to worry about changing
   all instances.
6. Leaving credentials in source code leads to poor password management in
   general. If changing a credential requires you to change code, you are less
   likely to want to do it. 

------------------------------------------------------------------------------
Status
------------------------------------------------------------------------------

This project is IN PROGRESS. All features and functionality are not working yet.

------------------------------------------------------------------------------
License
------------------------------------------------------------------------------

License: MIT (see LICENSE file)

------------------------------------------------------------------------------
Credits
------------------------------------------------------------------------------

Copyright 2010, YELLOWPAGES.COM LLC
Development by Neil Matatall <neil.matatall@gmail.com>

