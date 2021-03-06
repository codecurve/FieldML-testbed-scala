package framework.io.serialize

import fieldml.evaluator.ReferenceEvaluator
import fieldml.evaluator.Evaluator
import fieldml.valueType.ValueType

import util.exception._

import fieldml.jni.FieldmlApi._
import fieldml.jni.FieldmlHandleType._
import fieldml.jni.FieldmlApiConstants._

import framework.region.UserRegion
import framework.valuesource._


object ReferenceEvaluatorSerializer
{
    def insert[UserDofs](handle : Int, evaluator : ReferenceEvaluator[ValueSource[UserDofs]]) : Unit =
    {
        val remoteHandle = GetNamedObject(handle, evaluator.refEvaluator.name)
        val valueHandle = GetNamedObject(handle, evaluator.valueType.name)
        
        var objectHandle = Fieldml_CreateReferenceEvaluator(handle, evaluator.name, remoteHandle);
        
        for (pair <- evaluator.binds)
        {
            val valueTypeHandle = GetNamedObject(handle, pair._1.name)
            val sourceHandle = GetNamedObject(handle, pair._2.name)
            
            Fieldml_SetBind(handle, objectHandle, valueTypeHandle, sourceHandle)
        }
    }

    
    def extract[UserDofs](source : Deserializer[UserDofs], objectHandle : Int) :
        ReferenceEvaluatorValueSource[UserDofs] = 
    {
        val name = Fieldml_GetObjectName(source.fmlHandle, objectHandle)

        val remoteHandle = Fieldml_GetReferenceSourceEvaluator(source.fmlHandle, objectHandle)
        
        val evaluator : ValueSource[UserDofs] = source.getEvaluator(remoteHandle)
        
        val evaluatorType : ValueType = source.get(Fieldml_GetValueType(source.fmlHandle, objectHandle)).asInstanceOf[ValueType]
        val refEval = new ReferenceEvaluatorValueSource(name, evaluator, evaluatorType)
        
        for (b <- GetBinds(source, objectHandle)) refEval.bind(b)
        
        refEval
    }
}
