package framework.valuesource

import fieldml.domain._

import framework.value.Value
import framework.Context
import framework.EvaluationState

class ConstantValueSource( domain : Domain, _value : Value )
    extends ValueSource( domain )
{
    private val value = Some( _value )
    
    override def getValue( state : EvaluationState ) : Option[Value] = value
}
