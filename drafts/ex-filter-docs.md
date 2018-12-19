## esempio: proiezione campi da tuple

Supponendo di avere una collezione di oggetti (ad esempio `Stream` o `List`), ciascuno dei quali è una mappa `Map[String, Object]` che usiamo per rappresentare tuple provenienti da un database o da un triplestore, possiamo generare una collezione equivalente ma meno onerosa in memoria selezionando solo i campi che ci servono. Ci sono vari modi di farlo, tra cui le :

```scala
// collezione originale con molti campi
val elements:Stream[Map[String, Object]] = ... 
```

ad esempio trasformando ogni elemento:

```scala
val filtered_elements_1 = elements.map { doc =>
    Map(("uri"->doc.get("uri")), ("parent_uri"->doc.get("uri")))
}
```

oppure con le for comprehension:

```scala
val filtered_element_2 = for {

	docs <- elements.toStream
	d_uri <- docs.filter(_._1.equals("uri"))
	d_parent <- docs.filter(_._1.equals("parent_uri"))

} yield Map(d_uri, d_parent)
```
