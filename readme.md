Just a couple of utility classes for writing short applications, mainly revolving 
around wrapping the Java api which isn't covered by the Scala standard.

SqlConnection object:
```scala

import aghost7.scriptly._
import anorm._

import java.sql.Connection

val sqlUri = "jdbc:mysql://localhost:3306/test?user=root"

// driver loading is already handled if using postgresql or mysql.
SqlConnection(sqlUri){ implicit con =>
	val min = 1000
	val query = SQL(s"""
		SELECT ClassName, lvl FROM
		(
			SELECT class1 c, level1 lvl 
			FROM characterprofile
			WHERE level1 > 0 AND CurrentXP > $min
			UNION ALL
				SELECT class2 c, level2 lvl 
				FROM characterprofile
				WHERE level2 > 0 AND CurrentXP > $min
				UNION ALL
					SELECT class3 c, level3 lvl 
					FROM characterprofile
					WHERE level3 > 0 AND CurrentXP > $min
					UNION ALL
						SELECT class4 c, level4 lvl
						FROM characterprofile
						WHERE level4 > 0 AND CurrentXP > $min
		) asOneRow
		INNER JOIN classes 
			ON classes.ClassID = asOneRow.c
		ORDER BY ClassName
	""")()
		.map { row => (row[String]("ClassName"), row[Int]("lvl")) }
		.toList
	
	// do something with query result...
	
}// end of function closes connection.

```

Writer class:
```scala
Writer("greet.txt") { put =>
	put("Hello!")
}
```