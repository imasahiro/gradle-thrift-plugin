#/bin/sh -f

# things to do for travis-ci in the before_install section

if ( test "`uname -s`" = "Darwin" )
then
    brew install update
    brew install openssl
else
    echo
fi
