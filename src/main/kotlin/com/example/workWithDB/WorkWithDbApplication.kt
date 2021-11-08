package com.example.workWithDB

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

import com.example.workWithDB.Service.WatcherService
import org.springframework.jms.annotation.EnableJms

@SpringBootApplication
@EnableJms
class WorkWithDbApplication{
	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			val applicationContext = runApplication<WorkWithDbApplication>(*args)


			val watcherService: WatcherService = applicationContext.getBean("watcherService",WatcherService::class.java)
			watcherService.watchNewPersons()
		}
	}
}
