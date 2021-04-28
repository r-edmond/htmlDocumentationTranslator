package org.nu.htmlDocumentation
package example

import scala.xml.XML
import java.io.{File, PrintWriter}

object Main extends App {
  println("Nithyanandam")

  /* Step 1: Get Slide Data */

  //Variable and Value declarations
  val okFileExtensions = List("xml, rels")
  val dir = "D:\\code\\slides"
  val slideRelDir = "\\slides\\_rels"
  val slideDir = "\\slides"
  val imgDir = "\\media"

  //Get List of XMLs
  val f = getListOfFiles(new File("D:\\code\\slides"), okFileExtensions)

  //Store Slide Data in List
  var data = for (n <- f) yield getSlideData(dir + "\\" + n.getName)
  data = data.sortBy(n => n.slideNum)

  //Write slide data to txt, for test
  val pw = new PrintWriter(new File("D:\\code\\slides\\s.txt" ))

  //Store in Text for Stage 1
  data.foreach{
    n => if (n.text.nonEmpty) {
      pw.write("Slide Number: " + n.slideNum + "\n")
      pw.write(n.text)
      pw.write("\n")
    }
  }
  pw.close()

  println("Nithyanandam")

  //Create Slide Object
  case class Slide(text: String, slideNum: Int)

  //Get List of XML files
  def getListOfFiles(dir: File, extensions: List[String]): List[File] = {
    dir.listFiles.filter(_.isFile).toList.filter { file =>
      extensions.exists(file.getName.endsWith(_))
    }
  }

  //Get Slide Data based on Directory
  def getSlideData(directory: String): Slide = {
    //Load XML
    println("Loading XML...")
    val xml = XML.load(directory)

    //Node Variables
    val textNodes = xml \\ "sp"
    val tableNodes = xml \\ "tbl"

    //Get Slide Number
    val slideNum = directory.replaceAll("[^0-9]", "").toInt

    //Loop through each node
    println("Scanning for text..")
    val slideText = new StringBuilder()
    textNodes.foreach {
      nodeT => if (!nodeT.text.equals("")) {
        slideText.append(nodeT.text + "\n")
      }
    }
    println(slideText)

    //Finalizing Slide for Reference
    val s = Slide(slideText.toString(), slideNum)
    s
  }
}