#!/usr/bin/env python
# -*- Python -*-

"""
 \file foo.py
 \brief test module
 \date $Date$


"""
import sys
import time
sys.path.append(".")

# Import RTM module
import OpenRTM
import RTC

# Import Service implementation class
# <rtc-template block="service_impl">
from MyService_idl_example import *
from MyService2_idl_example import *

# </rtc-template>

# Import Service stub modules
# <rtc-template block="consumer_import">
import _GlobalIDL, _GlobalIDL__POA

# </rtc-template>


# This module's spesification
# <rtc-template block="module_spec">
foo_spec = ["implementation_id", "foo", 
		 "type_name",         "foo", 
		 "description",       "test module", 
		 "version",           "1.0.1", 
		 "vendor",            "TA", 
		 "category",          "sample", 
		 "activity_type",     "STATIC", 
		 "max_instance",      "2", 
		 "language",          "Python", 
		 "lang_type",         "SCRIPT",
		 ""]
# </rtc-template>

class foo(OpenRTM.DataFlowComponentBase):
	
	"""
	\class foo
	\brief test module
	
	"""
	def __init__(self, manager):
		"""
		\brief constructor
		\param manager Maneger Object
		"""
		OpenRTM.DataFlowComponentBase.__init__(self, manager)

		self._d_in1 = RTC.TimedShort(RTC.Time(0,0),0)
		"""
		"""
		self._in1In = OpenRTM.InPort("in1", self._d_in1, OpenRTM.RingBuffer(8))
		self._d_out1 = RTC.TimedLong(RTC.Time(0,0),0)
		"""
		"""
		self._out1Out = OpenRTM.OutPort("out1", self._d_out1, OpenRTM.RingBuffer(8))
		

		# Set InPort buffers
		self.registerInPort("in1",self._in1In)
		
		# Set OutPort buffers
		self.registerOutPort("out1",self._out1Out)
		

		"""
		"""
		self._MySVProPort = OpenRTM.CorbaPort("MySVPro")
		"""
		"""
		self._MySVPro2Port = OpenRTM.CorbaPort("MySVPro2")
		"""
		"""
		self._MyConProPort = OpenRTM.CorbaPort("MyConPro")
		"""
		"""
		self._MyConPro2Port = OpenRTM.CorbaPort("MyConPro2")
		

		"""
		"""
		self._myserviceP1 = MyService_i()
		"""
		"""
		self._myserviceP2 = MyService2_i()
		

		"""
		"""
		self._myservice0 = OpenRTM.CorbaConsumer(interfaceType=_GlobalIDL.MyService)
		"""
		"""
		self._myservice2 = OpenRTM.CorbaConsumer(interfaceType=_GlobalIDL.DAQService)
		
		# Set service provider to Ports
		self._MySVProPort.registerProvider("myserviceP1", "MyService", self._myserviceP1)
		self._MySVPro2Port.registerProvider("myserviceP2", "MyService2", self._myserviceP2)
		
		# Set service consumers to Ports
		self._MyConProPort.registerConsumer("myservice0", "MyService", self._myservice0)
		self._MyConPro2Port.registerConsumer("myservice2", "DAQService", self._myservice2)
		
		# Set CORBA Service Ports
		self.registerPort(self._MySVProPort)
		self.registerPort(self._MySVPro2Port)
		self.registerPort(self._MyConProPort)
		self.registerPort(self._MyConPro2Port)
		

		# initialize of configuration-data.
		# <rtc-template block="init_conf_param">
		
		# </rtc-template>


		 
	def onInitialize(self):
		"""
		
		The initialize action (on CREATED->ALIVE transition)
		formaer rtc_init_entry() 
		
		\return RTC::ReturnCode_t
		
		"""
		# Bind variables and configuration variable
		
		return RTC.RTC_OK


	
	#def onFinalize(self, ec_id):
	#	"""
	#
	#	The finalize action (on ALIVE->END transition)
	#	formaer rtc_exiting_entry()
	#
	#	\return RTC::ReturnCode_t
	#
	#	"""
	#
	#	return RTC.RTC_OK
	
	#def onStartup(self, ec_id):
	#	"""
	#
	#	The startup action when ExecutionContext startup
	#	former rtc_starting_entry()
	#
	#	\param ec_id target ExecutionContext Id
	#
	#	\return RTC::ReturnCode_t
	#
	#	"""
	#
	#	return RTC.RTC_OK
	
	#def onShutdown(self, ec_id):
	#	"""
	#
	#	The shutdown action when ExecutionContext stop
	#	former rtc_stopping_entry()
	#
	#	\param ec_id target ExecutionContext Id
	#
	#	\return RTC::ReturnCode_t
	#
	#	"""
	#
	#	return RTC.RTC_OK
	
	#def onActivated(self, ec_id):
	#	"""
	#
	#	The activated action (Active state entry action)
	#	former rtc_active_entry()
	#
	#	\param ec_id target ExecutionContext Id
	#
	#	\return RTC::ReturnCode_t
	#
	#	"""
	#
	#	return RTC.RTC_OK
	
	#def onDeactivated(self, ec_id):
	#	"""
	#
	#	The deactivated action (Active state exit action)
	#	former rtc_active_exit()
	#
	#	\param ec_id target ExecutionContext Id
	#
	#	\return RTC::ReturnCode_t
	#
	#	"""
	#
	#	return RTC.RTC_OK
	
	#def onExecute(self, ec_id):
	#	"""
	#
	#	The execution action that is invoked periodically
	#	former rtc_active_do()
	#
	#	\param ec_id target ExecutionContext Id
	#
	#	\return RTC::ReturnCode_t
	#
	#	"""
	#
	#	return RTC.RTC_OK
	
	#def onAborting(self, ec_id):
	#	"""
	#
	#	The aborting action when main logic error occurred.
	#	former rtc_aborting_entry()
	#
	#	\param ec_id target ExecutionContext Id
	#
	#	\return RTC::ReturnCode_t
	#
	#	"""
	#
	#	return RTC.RTC_OK
	
	#def onError(self, ec_id):
	#	"""
	#
	#	The error action in ERROR state
	#	former rtc_error_do()
	#
	#	\param ec_id target ExecutionContext Id
	#
	#	\return RTC::ReturnCode_t
	#
	#	"""
	#
	#	return RTC.RTC_OK
	
	#def onReset(self, ec_id):
	#	"""
	#
	#	The reset action that is invoked resetting
	#	This is same but different the former rtc_init_entry()
	#
	#	\param ec_id target ExecutionContext Id
	#
	#	\return RTC::ReturnCode_t
	#
	#	"""
	#
	#	return RTC.RTC_OK
	
	#def onStateUpdate(self, ec_id):
	#	"""
	#
	#	The state update action that is invoked after onExecute() action
	#	no corresponding operation exists in OpenRTm-aist-0.2.0
	#
	#	\param ec_id target ExecutionContext Id
	#
	#	\return RTC::ReturnCode_t
	#
	#	"""
	#
	#	return RTC.RTC_OK
	
	#def onRateChanged(self, ec_id):
	#	"""
	#
	#	The action that is invoked when execution context's rate is changed
	#	no corresponding operation exists in OpenRTm-aist-0.2.0
	#
	#	\param ec_id target ExecutionContext Id
	#
	#	\return RTC::ReturnCode_t
	#
	#	"""
	#
	#	return RTC.RTC_OK
	



def MyModuleInit(manager):
    profile = OpenRTM.Properties(defaults_str=foo_spec)
    manager.registerFactory(profile,
                            foo,
                            OpenRTM.Delete)

    # Create a component
    comp = manager.createComponent("foo")



def main():
	mgr = OpenRTM.Manager.init(len(sys.argv), sys.argv)
	mgr.setModuleInitProc(MyModuleInit)
	mgr.activateManager()
	mgr.runManager()

if __name__ == "__main__":
	main()

