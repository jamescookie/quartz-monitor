[![Build Status](https://travis-ci.org/robertoschwald/grails-schwartz-monitor.svg?branch=master)](https://travis-ci.org/robertoschwald/grails-schwartz-monitor)

# Schwartz Monitor Plugin for Grails

This plugin is a fork of the [quartz-monitor](https://grails.org/plugin/quartz-monitor) plugin and supports
the Grails [Quartz](https://plugins.grails.org/plugin/quartz) and Grails [Schwartz](https://plugins.grails.org/plugin/schwartz) plugins.

It allows you to view and administer all your Quartz job services in the web-ui.

## Prerequisites

This plugin requires the [Grails Quartz](http://plugins.grails.org/plugin/grails/quartz)
or [Grails Schwartz](http://plugins.grails.org/plugin/agileorbit/schwartz)
and [Asset Pipeline](http://grails.org/plugin/asset-pipeline) plugins to run.

## Installation

This plugin is a work-in-progress fork of the Grails Quartz Monitor plugin. For now it is not planned to release this plugin as a standalone plugin. We already sent a PR to the Grails-Quartz-Monitor repository to add Grails Schwartz support to the original plugin. See https://github.com/jamescookie/quartz-monitor/pull/42

If however you want to install this grails-schwartz-monitor plugin in the meantime until it is merged, perform:
```sh
gradle install
```

and add the plugin to your build.gradle dependencies:

```groovy
dependencies {
   ...
   compile "org.grails.plugins:schwartz-monitor:2.0-SNAPSHOT"
}
```

## Usage

Once you have the schwartz-monitor plugin installed and have created some job services, start your application and access the URL: 
http://localhost:8080/yourapp/quartz and you will find a list of all the Quartz job services you have created.


## Enhanced Experience

To have the page keep you constantly up to date requires [jQuery](http://grails.org/plugin/jquery). It will still work without jQuery,
but it won't look as good.

## Configuration

There are various configuration options, all start with `quartz.monitor`:

### layout

Allows you to change the sitemesh layout that page will use. Defaults to 'main'.

### showTriggerNames

If this is set to true, then the names of the triggers will be shown in the list - useful if you have multiple triggers for the same job.

### showCountdown

Will add javascript to the page in order to show a countdown to when the job will fire next, unless this is set to 'false'.

### showTickingClock

Will add javascript to the page in order to show a clock with the current time, unless this is set to 'false'.

## Documentation

http://robertoschwald.github.io/grails-schwartz-monitor

## Securing the Controller
If you use a security plugin (Spring-Security-Core, etc), you must ensure the controller methods are secured. E.g. in Spring-Security-Core, add a rule which is apropriate for your security needs:
```groovy
[pattern:'/quartz/**',              access:['ROLE_ADMIN']],
```

## Internals

Compared to the quartz-monitor plugin, this plugin is agnostic to the used quartz plugin, as it relies on Quartz itself and does not extend
any quartz-plugin factories.
Its implemented to register a org.quartz.JobListener, which listens to all Job tasks. 
This listener updates Job metrics in the QuartzMonitorService, which also provides additional figures like the startTime of a Job.

[![Build Status](https://travis-ci.org/robertoschwald/grails-schwartz-monitor.svg?branch=master)](http://travis-ci.org/robertoschwald/grails-schwartz-monitor)
