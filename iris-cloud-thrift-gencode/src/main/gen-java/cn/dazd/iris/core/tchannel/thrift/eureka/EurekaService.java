/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package cn.dazd.iris.core.tchannel.thrift.eureka;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-10-21")
public class EurekaService {

  public interface Iface {

    public cn.dazd.iris.core.tchannel.thrift.Result toEureka(cn.dazd.iris.core.tchannel.thrift.HostInfo hostInfo) throws org.apache.thrift.TException;

  }

  public interface AsyncIface {

    public void toEureka(cn.dazd.iris.core.tchannel.thrift.HostInfo hostInfo, org.apache.thrift.async.AsyncMethodCallback<cn.dazd.iris.core.tchannel.thrift.Result> resultHandler) throws org.apache.thrift.TException;

  }

  public static class Client extends org.apache.thrift.TServiceClient implements Iface {
    public static class Factory implements org.apache.thrift.TServiceClientFactory<Client> {
      public Factory() {}
      public Client getClient(org.apache.thrift.protocol.TProtocol prot) {
        return new Client(prot);
      }
      public Client getClient(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot) {
        return new Client(iprot, oprot);
      }
    }

    public Client(org.apache.thrift.protocol.TProtocol prot)
    {
      super(prot, prot);
    }

    public Client(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot) {
      super(iprot, oprot);
    }

    public cn.dazd.iris.core.tchannel.thrift.Result toEureka(cn.dazd.iris.core.tchannel.thrift.HostInfo hostInfo) throws org.apache.thrift.TException
    {
      send_toEureka(hostInfo);
      return recv_toEureka();
    }

    public void send_toEureka(cn.dazd.iris.core.tchannel.thrift.HostInfo hostInfo) throws org.apache.thrift.TException
    {
      toEureka_args args = new toEureka_args();
      args.setHostInfo(hostInfo);
      sendBase("toEureka", args);
    }

    public cn.dazd.iris.core.tchannel.thrift.Result recv_toEureka() throws org.apache.thrift.TException
    {
      toEureka_result result = new toEureka_result();
      receiveBase(result, "toEureka");
      if (result.isSetSuccess()) {
        return result.success;
      }
      throw new org.apache.thrift.TApplicationException(org.apache.thrift.TApplicationException.MISSING_RESULT, "toEureka failed: unknown result");
    }

  }
  public static class AsyncClient extends org.apache.thrift.async.TAsyncClient implements AsyncIface {
    public static class Factory implements org.apache.thrift.async.TAsyncClientFactory<AsyncClient> {
      private org.apache.thrift.async.TAsyncClientManager clientManager;
      private org.apache.thrift.protocol.TProtocolFactory protocolFactory;
      public Factory(org.apache.thrift.async.TAsyncClientManager clientManager, org.apache.thrift.protocol.TProtocolFactory protocolFactory) {
        this.clientManager = clientManager;
        this.protocolFactory = protocolFactory;
      }
      public AsyncClient getAsyncClient(org.apache.thrift.transport.TNonblockingTransport transport) {
        return new AsyncClient(protocolFactory, clientManager, transport);
      }
    }

    public AsyncClient(org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.async.TAsyncClientManager clientManager, org.apache.thrift.transport.TNonblockingTransport transport) {
      super(protocolFactory, clientManager, transport);
    }

    public void toEureka(cn.dazd.iris.core.tchannel.thrift.HostInfo hostInfo, org.apache.thrift.async.AsyncMethodCallback<cn.dazd.iris.core.tchannel.thrift.Result> resultHandler) throws org.apache.thrift.TException {
      checkReady();
      toEureka_call method_call = new toEureka_call(hostInfo, resultHandler, this, ___protocolFactory, ___transport);
      this.___currentMethod = method_call;
      ___manager.call(method_call);
    }

    public static class toEureka_call extends org.apache.thrift.async.TAsyncMethodCall<cn.dazd.iris.core.tchannel.thrift.Result> {
      private cn.dazd.iris.core.tchannel.thrift.HostInfo hostInfo;
      public toEureka_call(cn.dazd.iris.core.tchannel.thrift.HostInfo hostInfo, org.apache.thrift.async.AsyncMethodCallback<cn.dazd.iris.core.tchannel.thrift.Result> resultHandler, org.apache.thrift.async.TAsyncClient client, org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.transport.TNonblockingTransport transport) throws org.apache.thrift.TException {
        super(client, protocolFactory, transport, resultHandler, false);
        this.hostInfo = hostInfo;
      }

      public void write_args(org.apache.thrift.protocol.TProtocol prot) throws org.apache.thrift.TException {
        prot.writeMessageBegin(new org.apache.thrift.protocol.TMessage("toEureka", org.apache.thrift.protocol.TMessageType.CALL, 0));
        toEureka_args args = new toEureka_args();
        args.setHostInfo(hostInfo);
        args.write(prot);
        prot.writeMessageEnd();
      }

      public cn.dazd.iris.core.tchannel.thrift.Result getResult() throws org.apache.thrift.TException {
        if (getState() != org.apache.thrift.async.TAsyncMethodCall.State.RESPONSE_READ) {
          throw new java.lang.IllegalStateException("Method call not finished!");
        }
        org.apache.thrift.transport.TMemoryInputTransport memoryTransport = new org.apache.thrift.transport.TMemoryInputTransport(getFrameBuffer().array());
        org.apache.thrift.protocol.TProtocol prot = client.getProtocolFactory().getProtocol(memoryTransport);
        return (new Client(prot)).recv_toEureka();
      }
    }

  }

  public static class Processor<I extends Iface> extends org.apache.thrift.TBaseProcessor<I> implements org.apache.thrift.TProcessor {
    private static final org.slf4j.Logger _LOGGER = org.slf4j.LoggerFactory.getLogger(Processor.class.getName());
    public Processor(I iface) {
      super(iface, getProcessMap(new java.util.HashMap<java.lang.String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>>()));
    }

    protected Processor(I iface, java.util.Map<java.lang.String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>> processMap) {
      super(iface, getProcessMap(processMap));
    }

    private static <I extends Iface> java.util.Map<java.lang.String,  org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>> getProcessMap(java.util.Map<java.lang.String, org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> processMap) {
      processMap.put("toEureka", new toEureka());
      return processMap;
    }

    public static class toEureka<I extends Iface> extends org.apache.thrift.ProcessFunction<I, toEureka_args> {
      public toEureka() {
        super("toEureka");
      }

      public toEureka_args getEmptyArgsInstance() {
        return new toEureka_args();
      }

      protected boolean isOneway() {
        return false;
      }

      public toEureka_result getResult(I iface, toEureka_args args) throws org.apache.thrift.TException {
        toEureka_result result = new toEureka_result();
        result.success = iface.toEureka(args.hostInfo);
        return result;
      }
    }

  }

  public static class AsyncProcessor<I extends AsyncIface> extends org.apache.thrift.TBaseAsyncProcessor<I> {
    private static final org.slf4j.Logger _LOGGER = org.slf4j.LoggerFactory.getLogger(AsyncProcessor.class.getName());
    public AsyncProcessor(I iface) {
      super(iface, getProcessMap(new java.util.HashMap<java.lang.String, org.apache.thrift.AsyncProcessFunction<I, ? extends org.apache.thrift.TBase, ?>>()));
    }

    protected AsyncProcessor(I iface, java.util.Map<java.lang.String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase, ?>> processMap) {
      super(iface, getProcessMap(processMap));
    }

    private static <I extends AsyncIface> java.util.Map<java.lang.String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase,?>> getProcessMap(java.util.Map<java.lang.String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase, ?>> processMap) {
      processMap.put("toEureka", new toEureka());
      return processMap;
    }

    public static class toEureka<I extends AsyncIface> extends org.apache.thrift.AsyncProcessFunction<I, toEureka_args, cn.dazd.iris.core.tchannel.thrift.Result> {
      public toEureka() {
        super("toEureka");
      }

      public toEureka_args getEmptyArgsInstance() {
        return new toEureka_args();
      }

      public org.apache.thrift.async.AsyncMethodCallback<cn.dazd.iris.core.tchannel.thrift.Result> getResultHandler(final org.apache.thrift.server.AbstractNonblockingServer.AsyncFrameBuffer fb, final int seqid) {
        final org.apache.thrift.AsyncProcessFunction fcall = this;
        return new org.apache.thrift.async.AsyncMethodCallback<cn.dazd.iris.core.tchannel.thrift.Result>() { 
          public void onComplete(cn.dazd.iris.core.tchannel.thrift.Result o) {
            toEureka_result result = new toEureka_result();
            result.success = o;
            try {
              fcall.sendResponse(fb, result, org.apache.thrift.protocol.TMessageType.REPLY,seqid);
            } catch (org.apache.thrift.transport.TTransportException e) {
              _LOGGER.error("TTransportException writing to internal frame buffer", e);
              fb.close();
            } catch (java.lang.Exception e) {
              _LOGGER.error("Exception writing to internal frame buffer", e);
              onError(e);
            }
          }
          public void onError(java.lang.Exception e) {
            byte msgType = org.apache.thrift.protocol.TMessageType.REPLY;
            org.apache.thrift.TSerializable msg;
            toEureka_result result = new toEureka_result();
            if (e instanceof org.apache.thrift.transport.TTransportException) {
              _LOGGER.error("TTransportException inside handler", e);
              fb.close();
              return;
            } else if (e instanceof org.apache.thrift.TApplicationException) {
              _LOGGER.error("TApplicationException inside handler", e);
              msgType = org.apache.thrift.protocol.TMessageType.EXCEPTION;
              msg = (org.apache.thrift.TApplicationException)e;
            } else {
              _LOGGER.error("Exception inside handler", e);
              msgType = org.apache.thrift.protocol.TMessageType.EXCEPTION;
              msg = new org.apache.thrift.TApplicationException(org.apache.thrift.TApplicationException.INTERNAL_ERROR, e.getMessage());
            }
            try {
              fcall.sendResponse(fb,msg,msgType,seqid);
            } catch (java.lang.Exception ex) {
              _LOGGER.error("Exception writing to internal frame buffer", ex);
              fb.close();
            }
          }
        };
      }

      protected boolean isOneway() {
        return false;
      }

      public void start(I iface, toEureka_args args, org.apache.thrift.async.AsyncMethodCallback<cn.dazd.iris.core.tchannel.thrift.Result> resultHandler) throws org.apache.thrift.TException {
        iface.toEureka(args.hostInfo,resultHandler);
      }
    }

  }

  public static class toEureka_args implements org.apache.thrift.TBase<toEureka_args, toEureka_args._Fields>, java.io.Serializable, Cloneable, Comparable<toEureka_args>   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("toEureka_args");

    private static final org.apache.thrift.protocol.TField HOST_INFO_FIELD_DESC = new org.apache.thrift.protocol.TField("hostInfo", org.apache.thrift.protocol.TType.STRUCT, (short)1);

    private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new toEureka_argsStandardSchemeFactory();
    private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new toEureka_argsTupleSchemeFactory();

    private cn.dazd.iris.core.tchannel.thrift.HostInfo hostInfo; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      HOST_INFO((short)1, "hostInfo");

      private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

      static {
        for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 1: // HOST_INFO
            return HOST_INFO;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(java.lang.String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final java.lang.String _fieldName;

      _Fields(short thriftId, java.lang.String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public java.lang.String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.HOST_INFO, new org.apache.thrift.meta_data.FieldMetaData("hostInfo", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, cn.dazd.iris.core.tchannel.thrift.HostInfo.class)));
      metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(toEureka_args.class, metaDataMap);
    }

    public toEureka_args() {
    }

    public toEureka_args(
      cn.dazd.iris.core.tchannel.thrift.HostInfo hostInfo)
    {
      this();
      this.hostInfo = hostInfo;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public toEureka_args(toEureka_args other) {
      if (other.isSetHostInfo()) {
        this.hostInfo = new cn.dazd.iris.core.tchannel.thrift.HostInfo(other.hostInfo);
      }
    }

    public toEureka_args deepCopy() {
      return new toEureka_args(this);
    }

    @Override
    public void clear() {
      this.hostInfo = null;
    }

    public cn.dazd.iris.core.tchannel.thrift.HostInfo getHostInfo() {
      return this.hostInfo;
    }

    public toEureka_args setHostInfo(cn.dazd.iris.core.tchannel.thrift.HostInfo hostInfo) {
      this.hostInfo = hostInfo;
      return this;
    }

    public void unsetHostInfo() {
      this.hostInfo = null;
    }

    /** Returns true if field hostInfo is set (has been assigned a value) and false otherwise */
    public boolean isSetHostInfo() {
      return this.hostInfo != null;
    }

    public void setHostInfoIsSet(boolean value) {
      if (!value) {
        this.hostInfo = null;
      }
    }

    public void setFieldValue(_Fields field, java.lang.Object value) {
      switch (field) {
      case HOST_INFO:
        if (value == null) {
          unsetHostInfo();
        } else {
          setHostInfo((cn.dazd.iris.core.tchannel.thrift.HostInfo)value);
        }
        break;

      }
    }

    public java.lang.Object getFieldValue(_Fields field) {
      switch (field) {
      case HOST_INFO:
        return getHostInfo();

      }
      throw new java.lang.IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new java.lang.IllegalArgumentException();
      }

      switch (field) {
      case HOST_INFO:
        return isSetHostInfo();
      }
      throw new java.lang.IllegalStateException();
    }

    @Override
    public boolean equals(java.lang.Object that) {
      if (that == null)
        return false;
      if (that instanceof toEureka_args)
        return this.equals((toEureka_args)that);
      return false;
    }

    public boolean equals(toEureka_args that) {
      if (that == null)
        return false;
      if (this == that)
        return true;

      boolean this_present_hostInfo = true && this.isSetHostInfo();
      boolean that_present_hostInfo = true && that.isSetHostInfo();
      if (this_present_hostInfo || that_present_hostInfo) {
        if (!(this_present_hostInfo && that_present_hostInfo))
          return false;
        if (!this.hostInfo.equals(that.hostInfo))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      int hashCode = 1;

      hashCode = hashCode * 8191 + ((isSetHostInfo()) ? 131071 : 524287);
      if (isSetHostInfo())
        hashCode = hashCode * 8191 + hostInfo.hashCode();

      return hashCode;
    }

    @Override
    public int compareTo(toEureka_args other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;

      lastComparison = java.lang.Boolean.valueOf(isSetHostInfo()).compareTo(other.isSetHostInfo());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetHostInfo()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.hostInfo, other.hostInfo);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      scheme(iprot).read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      scheme(oprot).write(oprot, this);
    }

    @Override
    public java.lang.String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder("toEureka_args(");
      boolean first = true;

      sb.append("hostInfo:");
      if (this.hostInfo == null) {
        sb.append("null");
      } else {
        sb.append(this.hostInfo);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
      // check for sub-struct validity
      if (hostInfo != null) {
        hostInfo.validate();
      }
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
      try {
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class toEureka_argsStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
      public toEureka_argsStandardScheme getScheme() {
        return new toEureka_argsStandardScheme();
      }
    }

    private static class toEureka_argsStandardScheme extends org.apache.thrift.scheme.StandardScheme<toEureka_args> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, toEureka_args struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 1: // HOST_INFO
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.hostInfo = new cn.dazd.iris.core.tchannel.thrift.HostInfo();
                struct.hostInfo.read(iprot);
                struct.setHostInfoIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, toEureka_args struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.hostInfo != null) {
          oprot.writeFieldBegin(HOST_INFO_FIELD_DESC);
          struct.hostInfo.write(oprot);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class toEureka_argsTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
      public toEureka_argsTupleScheme getScheme() {
        return new toEureka_argsTupleScheme();
      }
    }

    private static class toEureka_argsTupleScheme extends org.apache.thrift.scheme.TupleScheme<toEureka_args> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, toEureka_args struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
        java.util.BitSet optionals = new java.util.BitSet();
        if (struct.isSetHostInfo()) {
          optionals.set(0);
        }
        oprot.writeBitSet(optionals, 1);
        if (struct.isSetHostInfo()) {
          struct.hostInfo.write(oprot);
        }
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, toEureka_args struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
        java.util.BitSet incoming = iprot.readBitSet(1);
        if (incoming.get(0)) {
          struct.hostInfo = new cn.dazd.iris.core.tchannel.thrift.HostInfo();
          struct.hostInfo.read(iprot);
          struct.setHostInfoIsSet(true);
        }
      }
    }

    private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
      return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
    }
  }

  public static class toEureka_result implements org.apache.thrift.TBase<toEureka_result, toEureka_result._Fields>, java.io.Serializable, Cloneable, Comparable<toEureka_result>   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("toEureka_result");

    private static final org.apache.thrift.protocol.TField SUCCESS_FIELD_DESC = new org.apache.thrift.protocol.TField("success", org.apache.thrift.protocol.TType.STRUCT, (short)0);

    private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new toEureka_resultStandardSchemeFactory();
    private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new toEureka_resultTupleSchemeFactory();

    private cn.dazd.iris.core.tchannel.thrift.Result success; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      SUCCESS((short)0, "success");

      private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

      static {
        for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 0: // SUCCESS
            return SUCCESS;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(java.lang.String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final java.lang.String _fieldName;

      _Fields(short thriftId, java.lang.String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public java.lang.String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.SUCCESS, new org.apache.thrift.meta_data.FieldMetaData("success", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, cn.dazd.iris.core.tchannel.thrift.Result.class)));
      metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(toEureka_result.class, metaDataMap);
    }

    public toEureka_result() {
    }

    public toEureka_result(
      cn.dazd.iris.core.tchannel.thrift.Result success)
    {
      this();
      this.success = success;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public toEureka_result(toEureka_result other) {
      if (other.isSetSuccess()) {
        this.success = new cn.dazd.iris.core.tchannel.thrift.Result(other.success);
      }
    }

    public toEureka_result deepCopy() {
      return new toEureka_result(this);
    }

    @Override
    public void clear() {
      this.success = null;
    }

    public cn.dazd.iris.core.tchannel.thrift.Result getSuccess() {
      return this.success;
    }

    public toEureka_result setSuccess(cn.dazd.iris.core.tchannel.thrift.Result success) {
      this.success = success;
      return this;
    }

    public void unsetSuccess() {
      this.success = null;
    }

    /** Returns true if field success is set (has been assigned a value) and false otherwise */
    public boolean isSetSuccess() {
      return this.success != null;
    }

    public void setSuccessIsSet(boolean value) {
      if (!value) {
        this.success = null;
      }
    }

    public void setFieldValue(_Fields field, java.lang.Object value) {
      switch (field) {
      case SUCCESS:
        if (value == null) {
          unsetSuccess();
        } else {
          setSuccess((cn.dazd.iris.core.tchannel.thrift.Result)value);
        }
        break;

      }
    }

    public java.lang.Object getFieldValue(_Fields field) {
      switch (field) {
      case SUCCESS:
        return getSuccess();

      }
      throw new java.lang.IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new java.lang.IllegalArgumentException();
      }

      switch (field) {
      case SUCCESS:
        return isSetSuccess();
      }
      throw new java.lang.IllegalStateException();
    }

    @Override
    public boolean equals(java.lang.Object that) {
      if (that == null)
        return false;
      if (that instanceof toEureka_result)
        return this.equals((toEureka_result)that);
      return false;
    }

    public boolean equals(toEureka_result that) {
      if (that == null)
        return false;
      if (this == that)
        return true;

      boolean this_present_success = true && this.isSetSuccess();
      boolean that_present_success = true && that.isSetSuccess();
      if (this_present_success || that_present_success) {
        if (!(this_present_success && that_present_success))
          return false;
        if (!this.success.equals(that.success))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      int hashCode = 1;

      hashCode = hashCode * 8191 + ((isSetSuccess()) ? 131071 : 524287);
      if (isSetSuccess())
        hashCode = hashCode * 8191 + success.hashCode();

      return hashCode;
    }

    @Override
    public int compareTo(toEureka_result other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;

      lastComparison = java.lang.Boolean.valueOf(isSetSuccess()).compareTo(other.isSetSuccess());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetSuccess()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.success, other.success);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      scheme(iprot).read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      scheme(oprot).write(oprot, this);
      }

    @Override
    public java.lang.String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder("toEureka_result(");
      boolean first = true;

      sb.append("success:");
      if (this.success == null) {
        sb.append("null");
      } else {
        sb.append(this.success);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
      // check for sub-struct validity
      if (success != null) {
        success.validate();
      }
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
      try {
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class toEureka_resultStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
      public toEureka_resultStandardScheme getScheme() {
        return new toEureka_resultStandardScheme();
      }
    }

    private static class toEureka_resultStandardScheme extends org.apache.thrift.scheme.StandardScheme<toEureka_result> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, toEureka_result struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 0: // SUCCESS
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.success = new cn.dazd.iris.core.tchannel.thrift.Result();
                struct.success.read(iprot);
                struct.setSuccessIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, toEureka_result struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.success != null) {
          oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
          struct.success.write(oprot);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class toEureka_resultTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
      public toEureka_resultTupleScheme getScheme() {
        return new toEureka_resultTupleScheme();
      }
    }

    private static class toEureka_resultTupleScheme extends org.apache.thrift.scheme.TupleScheme<toEureka_result> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, toEureka_result struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
        java.util.BitSet optionals = new java.util.BitSet();
        if (struct.isSetSuccess()) {
          optionals.set(0);
        }
        oprot.writeBitSet(optionals, 1);
        if (struct.isSetSuccess()) {
          struct.success.write(oprot);
        }
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, toEureka_result struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
        java.util.BitSet incoming = iprot.readBitSet(1);
        if (incoming.get(0)) {
          struct.success = new cn.dazd.iris.core.tchannel.thrift.Result();
          struct.success.read(iprot);
          struct.setSuccessIsSet(true);
        }
      }
    }

    private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
      return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
    }
  }

}
