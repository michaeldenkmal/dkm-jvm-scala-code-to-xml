import scala.meta._
import org.dom4j.Document
import org.dom4j.DocumentHelper
import org.dom4j.Element
import org.dom4j.io.{OutputFormat, XMLWriter}

import java.io.FileWriter
import java.nio.file.{Path, Paths}

object Main {
    def main(args: Array[String]): Unit = {
        // Read Scala source code from a file
        val fp = "D:\\projekte\\oh\\oh_java_mv\\wa_oh\\src\\main\\scala\\at\\denkmal\\libohws\\ws\\GpRes.scala"
        val source = scala.io.Source.fromFile(fp)
        val code = try source.mkString finally source.close()

        // Parse Scala code into an abstract syntax tree (AST)
        val ast = code.parse[Source].get

        def printastNode(astNode: Tree, level: Int): Unit = {
            val padding = Range(0, level).map(
                i => "-"
            ).mkString("")

            val info = astNode.productElementNames.
                filter(attrName => attrName != "stats").
                map(attrName => {
                    val attrValue: Any =
                        astNode.productElement(astNode.productElementNames.toList.indexOf(attrName))
                    s"$attrName = $attrValue"
                }).mkString(";")

            println(padding + astNode.productPrefix + ":" + info)

            astNode.children.foreach(child => {
                printastNode(child, level + 2)
            })

        }

        //printastNode(astNode = ast,level = 0)


        //println(ast)
        // Convert AST to XML
        val xmlAst = convertAstToXmlDocument(ast)

        // Print the XML representation of the AST
        //println(xmlAst.asXML())
        val output = Paths.get(s"$fp.xml")
        saveToXmlFile(xmlAst, output)
        println(output)
    }

    def saveToXmlFile(xmlDoc: Document, outPath: Path): Unit = {
        //// lets write to a file
        //        try (FileWriter fileWriter = new FileWriter("output.xml")) {
        //            XMLWriter writer = new XMLWriter(fileWriter);
        //            writer.write( document );
        //            writer.close();
        //        }

        try {
            val fileWriter = new FileWriter(outPath.toFile)
            val format = OutputFormat.createPrettyPrint
            val xmlWriter = new XMLWriter(fileWriter, format)
            xmlWriter.write(xmlDoc)
            xmlWriter.flush()
            xmlWriter.close()
        } catch {
            case err: Throwable => throw new Error(s"${err.getMessage}: outPatu=@@${outPath}@@: ${err.toString}")
        }


        //
        //        // Pretty print the document to System.out
        //        OutputFormat format = OutputFormat.createPrettyPrint();
        //        writer = new XMLWriter(System.out, format);
        //        writer.write( document );
        //
        //        // Compact format to System.out
        //        format = OutputFormat.createCompactFormat();
        //        writer = new XMLWriter(System.out, format);
        //        writer.write(document);
        //        writer.close();
    }

    def convertAstToXmlDocument(ast: Tree): Document = {
        def convertNode(astNode: Tree, xmlParent: Element): Unit = {
            val elemName = cleanTagAndAttrName(astNode.productPrefix)
            val retElem = xmlParent.addElement(elemName)

            astNode.productElementNames.
                filter(attrName => (attrName != "stats")).
                foreach(attrName => {
                    val attrValue: Any =
                        astNode.productElement(astNode.productElementNames.toList.indexOf(attrName))
                    retElem.addAttribute(cleanTagAndAttrName(attrName), attrValue.toString)
                })

            astNode.children.foreach(child => {
                convertNode(child, retElem)
            })
        }

        val document = DocumentHelper.createDocument
        val root: Element = document.addElement("root")
        convertNode(astNode = ast, xmlParent = root)
        document
    }

    def cleanTagAndAttrName(name:String):String ={
        if (name == null) {
            return ""
        }
        name.replace(".","_").toLowerCase()
    }
}
