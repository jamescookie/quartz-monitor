# Quartz Monitor Plugin for Grails

This Grails plugin allows you to monitor and control Quartz jobs. 
It supports the Grails [Quartz](https://plugins.grails.org/plugin/quartz) and Grails [Schwartz](https://plugins.grails.org/plugin/schwartz) plugins.

It allows you to view and administer all your Quartz jobs in the web-ui.

## Prerequisites

This plugin requires the Grails Quartz or Grails Schwartz and [Asset Pipeline](http://grails.org/plugin/asset-pipeline) plugins to run.

## Usage

Once you have the Quartz/Schwartz plugin installed and have created some job services, then you will probably start wondering if they 
are all running as desired.

Simply add this plugin as a dependency to build.gradle, start your application and access the URL: 
http://localhost:8080/yourapp/quartz and you will find a list of all the Quartz job services you have created.

```groovy
dependencies {
   ...
   compile quartz
}
```

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

## Internals

This plugin is agnostic to the used Grails quartz plugin, as it relies on Quartz itself and does not extend any quartz-plugin factories.
It registers a org.quartz.JobListener, which listens to all Job tasks. 
This listener updates Job metrics in the QuartzMonitorService, which also provides additional figures like the startTime of a Job.