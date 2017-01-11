db = connect("localhost/blog");

db.users.createIndex({ dni: 1 }, {unique: true} )
db.articles.createIndex({ title: 1 }, {unique: true} )

for (i=1 ; i <= 5 ; i ++) {
	db.users.insert({dni: "00000000" + i, name: "User" + i, email: "emailUser" + i + "@gmail.com"})
	db.articles.insert({
						title: "Title" + i,
						author: "User" + i,
						publish_date: ISODate("2013-10-02T01:11:18.965Z"),
						text: "Lorem ipsum ...",
						comments: [
							{
								author: "User1",
								text: "Comment lorem... ",
								date: ISODate("2013-10-02T01:11:18.965Z"),
							},
							{
								author: "User2",
								text: "Comment lorem... ",
								date: ISODate("2013-10-02T01:11:18.965Z"),
							},
							{
								author: "User3",
								text: "Comment lorem... ",
								date: ISODate("2013-10-02T01:11:18.965Z"),
							}
						]
					})

}