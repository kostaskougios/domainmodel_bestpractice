domainmodel_bestpractice
========================

best practices for java domain models

- builders
- static factories
- immutability
- fail fast
- annotations

package sample.domain (src & test) contains the good stuff, sample.mutabledomain contains the bad stuff!

The User class is the main demo class. It can have an empty password for user's that didn't set their password,
and an encrypted password for secured user accounts. For the sake of demo, user's can have unencrypted passwords
as well as encrypted ones and encryption is done via md5.

The test cases for the 2 User classes show the benefits of sample.doman.User over sample.mutabledomain.User .
