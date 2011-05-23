package org.grails.plugins.quartz

class QuartzController {
    def jobManagerService

    def index = {
        redirect(action: "list")
    }

    def list = {
        [jobs:jobManagerService.getAllJobs()]
    }
}
