cors-plugin
===========

Jenkins CORS plugin

Extend Jenkins to supply <a href='http://www.w3.org/TR/cors/' target='_blank'>CORS</a> header for cloudbees.

This allows localhost.cloudbees.com:8080, dashboard.cloudbees.com and dashboard.beescloud.com to do direct CORS calls from browsers.

Because browser implementers are hateful evil people - the W3C spec for multiple headers is not followed very closely, so this plugin
will return one header back out of the list above, that matches the Origin header.

