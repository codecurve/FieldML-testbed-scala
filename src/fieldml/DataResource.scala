package fieldml


class DataResource( name : String )
    extends FieldmlObject( name )
{
    val sources = ArrayBuffer[DataSource]()
}