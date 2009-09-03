# Python stubs generated by omniidl from MyService2.idl

import omniORB, _omnipy
from omniORB import CORBA, PortableServer
_0_CORBA = CORBA

_omnipy.checkVersion(2,0, __file__)

try:
    _omniORB_StructBase = omniORB.StructBase
except AttributeError:
    class _omniORB_StructBase: pass


#
# Start of module "_GlobalIDL"
#
__name__ = "_GlobalIDL"
_0__GlobalIDL = omniORB.openModule("_GlobalIDL", r"MyService2.idl")
_0__GlobalIDL__POA = omniORB.openModule("_GlobalIDL__POA", r"MyService2.idl")


# interface MyService2
_0__GlobalIDL._d_MyService2 = (omniORB.tcInternal.tv_objref, "IDL:MyService2:1.0", "MyService2")
omniORB.typeMapping["IDL:MyService2:1.0"] = _0__GlobalIDL._d_MyService2
_0__GlobalIDL.MyService2 = omniORB.newEmptyClass()
class MyService2 :
    _NP_RepositoryId = _0__GlobalIDL._d_MyService2[1]

    def __init__(self, *args, **kw):
        raise RuntimeError("Cannot construct objects of this type.")

    _nil = CORBA.Object._nil


_0__GlobalIDL.MyService2 = MyService2
_0__GlobalIDL._tc_MyService2 = omniORB.tcInternal.createTypeCode(_0__GlobalIDL._d_MyService2)
omniORB.registerType(MyService2._NP_RepositoryId, _0__GlobalIDL._d_MyService2, _0__GlobalIDL._tc_MyService2)

# MyService2 operations and attributes
MyService2._d_setKpGain = ((omniORB.tcInternal.tv_float, ), (), None)
MyService2._d_getKpGain = ((), (omniORB.tcInternal.tv_float, ), None)

# MyService2 object reference
class _objref_MyService2 (CORBA.Object):
    _NP_RepositoryId = MyService2._NP_RepositoryId

    def __init__(self):
        CORBA.Object.__init__(self)

    def setKpGain(self, *args):
        return _omnipy.invoke(self, "setKpGain", _0__GlobalIDL.MyService2._d_setKpGain, args)

    def getKpGain(self, *args):
        return _omnipy.invoke(self, "getKpGain", _0__GlobalIDL.MyService2._d_getKpGain, args)

    __methods__ = ["setKpGain", "getKpGain"] + CORBA.Object.__methods__

omniORB.registerObjref(MyService2._NP_RepositoryId, _objref_MyService2)
_0__GlobalIDL._objref_MyService2 = _objref_MyService2
del MyService2, _objref_MyService2

# MyService2 skeleton
__name__ = "_GlobalIDL__POA"
class MyService2 (PortableServer.Servant):
    _NP_RepositoryId = _0__GlobalIDL.MyService2._NP_RepositoryId


    _omni_op_d = {"setKpGain": _0__GlobalIDL.MyService2._d_setKpGain, "getKpGain": _0__GlobalIDL.MyService2._d_getKpGain}

MyService2._omni_skeleton = MyService2
_0__GlobalIDL__POA.MyService2 = MyService2
del MyService2
__name__ = "_GlobalIDL"

#
# End of module "_GlobalIDL"
#
__name__ = "MyService2_idl"

_exported_modules = ( "_GlobalIDL", )

# The end.