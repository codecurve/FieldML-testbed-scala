package fieldml.evaluator

import scala.collection.mutable.Map

import fieldml.valueType.ValueType
import fieldml.FieldmlObject

abstract class ReferenceEvaluator( name : String, valueDomain : ValueType, val refEvaluator : Evaluator ) 
    extends Evaluator( name, valueDomain )
{
    val binds = Map[ AbstractEvaluator, Evaluator ]()
    

    def variables = refEvaluator.variables

    
    def bind( _bind : Tuple2[ AbstractEvaluator, Evaluator ] )
    {
        binds( _bind._1 ) = _bind._2
    }
}