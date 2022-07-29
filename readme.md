## postgrest-java 

#### INIT
```java
PostgrestClient client = new PostgrestClient("http://localhost");
```


#### RPC

```java
Map<String, String> body = new HashMap<>();

body.put("name", "123");
body.put("age", "13");
    
client.rpc("method", body).execute();

```

#### SELECT

```java
Optional<PostgrestResponse> response = client.from("table_name")
    .select("*")
    .eq("num_iid",1)
    .execute()
    
if(response.isPresent()){return response.getBody()};

// select * from table_name where num_iid = 1 limit 1;
ItemDTO item=client.from("table_name")
  .select("*")
  .eq("num_iid",1)
  .executeAndGetSingle(ItemDTO.class);

// select seller_id AS sellerId ...
ItemDTO item=client.from("table_name")
    .select("sellerId:seller_id,name,numIid:num_iid")
    .eq("num_iid",1)
    .executeAndGetSingle(ItemDTO.class);

// select num_iid,title,genders from table_name where seller_id != 1;
List<ItemDTO> items = client.from("table_name")
  .select("num_iid,title,genders")
  .neq("seller_id",1)
  .executeAndGetList(ItemDTO.class);
```
    
#### INSERT    
```java
Map<String, String> item = new HashMap<>();
item.put("name","test");

// single insert.
client.from("table_name")
  .insert(item)
  .execute();

// batch insert.
client.from("table_name")
  .insert(Collections.singletonList(item))
  .execute();
```

#### DELETE
```java

// delete from table_name where id = 1
client.from("table_name")
    .delete()
    .eq("id", 1)
    .execute()
    
    
// will return the count of delete.
PostgrestResponse respone = client.from("table_name")
    .delete(Returning.REPRESENTATION, Count.EXACT)
    .eq("id", 1)
    .execute()
response.getCount();
```

#### UPDATE
```java

Map<String, String> item = new HashMap<>();
item.put("name","test");

// update table_name set name='test' where id = 1;
client.from("table_name")
    .update(item)
    .eq("id", 1)
    .execute()
    
// get update count.    
PostgrestResponse response = client.from("table_name")
    .update(item, Returning.REPRESENTATION, Count.EXACT)
    .eq("id", 1)
    .execute()

response.getCount();
```
