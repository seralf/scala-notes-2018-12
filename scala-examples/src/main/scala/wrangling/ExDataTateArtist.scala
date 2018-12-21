package wrangling

import scala.io.Source

object ExDataTateArtist extends App {

  val artists_url = "https://raw.githubusercontent.com/tategallery/collection/master/artist_data.csv"

  val src = Source.fromURL(artists_url)("UTF-8")

  val content = src.getLines().toStream

  src.close()

  
  def action(){
    
  }
  
}