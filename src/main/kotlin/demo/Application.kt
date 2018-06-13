package demo

import org.springframework.beans.factory.InitializingBean
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.registerBean
import org.springframework.fu.AbstractModule
import org.springframework.fu.ApplicationDsl
import org.springframework.fu.application

val app = application {
	extraModule("Mario", { it.toUpperCase() })
}

fun ApplicationDsl.extraModule(name: String, cb: (String) -> String = { name -> name }): ExtraModule {
	val element = ExtraModule(name, cb)
	this.initializers.add(element)
	return element
}

class ExtraModule(val name: String, val callback: (String) -> String) : AbstractModule() {

	override fun initialize(context: GenericApplicationContext) {
		context.registerBean {
			InitializingBean {
				println("Hello ${callback(name)}!")
			}
		}
	}
}

fun main(args: Array<String>) = app.run(await = true)
