
package views.html

import play.templates._
import play.templates.TemplateMagic._

import play.api.templates._
import play.api.templates.PlayMagic._
import models._
import controllers._
import java.lang._
import java.util._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import play.api.i18n._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.data._
import play.api.data.Field
import play.mvc.Http.Context.Implicit._
import views.html._
/**/
object index extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template1[String,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(message: String):play.api.templates.Html = {
        _display_ {

Seq[Any](format.raw/*1.19*/("""


"""),_display_(Seq[Any](/*4.2*/main("Welcome to Play 2.1")/*4.29*/ {_display_(Seq[Any](format.raw/*4.31*/("""
    
    """),_display_(Seq[Any](/*6.6*/message)),format.raw/*6.13*/("""
    
""")))})),format.raw/*8.2*/("""
"""))}
    }
    
    def render(message:String): play.api.templates.Html = apply(message)
    
    def f:((String) => play.api.templates.Html) = (message) => apply(message)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Fri Feb 22 10:38:31 EET 2013
                    SOURCE: /home/alex/Dropbox/Web-Talk/HelloPlay/app/views/index.scala.html
                    HASH: 0249b773b4c8458a4048d57290837f821a5e028f
                    MATRIX: 723->1|817->18|855->22|890->49|929->51|974->62|1002->69|1039->76
                    LINES: 26->1|29->1|32->4|32->4|32->4|34->6|34->6|36->8
                    -- GENERATED --
                */
            