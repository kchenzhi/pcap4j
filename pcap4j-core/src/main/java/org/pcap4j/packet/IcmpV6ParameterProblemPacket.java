/*_##########################################################################
  _##
  _##  Copyright (C) 2012-2014  Kaito Yamada
  _##
  _##########################################################################
*/

package org.pcap4j.packet;

import static org.pcap4j.util.ByteArrays.*;
import java.util.ArrayList;
import java.util.List;
import org.pcap4j.util.ByteArrays;

/**
 * @author Kaito Yamada
 * @since pcap4j 0.9.15
 */
public final class IcmpV6ParameterProblemPacket
extends IcmpV6InvokingPacketPacket {

  /**
   *
   */
  private static final long serialVersionUID = -7613453030792043352L;

  private final IcmpV6ParameterProblemHeader header;

  /**
   *
   * @param rawData
   * @return a new IcmpV6ParameterProblemPacket object.
   * @throws IllegalRawDataException
   * @throws NullPointerException if the rawData argument is null.
   * @throws IllegalArgumentException if the rawData argument is empty.
   */
  public static IcmpV6ParameterProblemPacket newPacket(
    byte[] rawData
  ) throws IllegalRawDataException {
    if (rawData == null) {
      throw new NullPointerException("rawData must not be null.");
    }
    if (rawData.length == 0) {
      throw new IllegalArgumentException("rawData is empty.");
    }

    IcmpV6ParameterProblemHeader header = new IcmpV6ParameterProblemHeader(rawData);

    int payloadLength = rawData.length - header.length();
    if (payloadLength > 0) {
      byte[] rawPayload
        = ByteArrays.getSubArray(
            rawData,
            header.length(),
            payloadLength
          );
      return new IcmpV6ParameterProblemPacket(header, rawPayload);
    }
    else {
      return new IcmpV6ParameterProblemPacket(header);
    }
  }

  private IcmpV6ParameterProblemPacket(IcmpV6ParameterProblemHeader header) {
    this.header = header;
  }

  private IcmpV6ParameterProblemPacket(
    IcmpV6ParameterProblemHeader header, byte[] rawPayload
  ) {
    super(rawPayload);
    this.header = header;
  }

  private IcmpV6ParameterProblemPacket(Builder builder) {
    super(builder);
    this.header = new IcmpV6ParameterProblemHeader(builder);
  }

  @Override
  public IcmpV6ParameterProblemHeader getHeader() {
    return header;
  }

  @Override
  public Builder getBuilder() {
    return new Builder(this);
  }

  /**
   * @author Kaito Yamada
   * @since pcap4j 0.9.15
   */
  public static
  final class Builder extends org.pcap4j.packet.IcmpV6InvokingPacketPacket.Builder {

    private int pointer;

    /**
     *
     */
    public Builder() {}

    private Builder(IcmpV6ParameterProblemPacket packet) {
      super(packet);
      this.pointer = packet.getHeader().pointer;
    }

    /**
     *
     * @param pointer
     * @return this Builder object for method chaining.
     */
    public Builder pointer(int pointer) {
      this.pointer = pointer;
      return this;
    }

    @Override
    public Builder payload(Packet payload) {
      super.payload(payload);
      return this;
    }

    @Override
    public IcmpV6ParameterProblemPacket build() {
      return new IcmpV6ParameterProblemPacket(this);
    }

  }

  /**
   * @author Kaito Yamada
   * @since pcap4j 0.9.15
   */
  public static final class IcmpV6ParameterProblemHeader extends AbstractHeader {

    /*
     *   0                            15                              31
     *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     *  |                             pointer                           |
     *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     *
     */

    /**
     *
     */
    private static final long serialVersionUID = -3743068221589212767L;

    private static final int POINTER_OFFSET
      = 0;
    private static final int POINTER_SIZE
      = INT_SIZE_IN_BYTES;
    private static final int ICMPV6_PARAMETER_PROBLEM_HEADER_SIZE
      = POINTER_OFFSET + POINTER_SIZE;

    private final int pointer;

    private IcmpV6ParameterProblemHeader(byte[] rawData) throws IllegalRawDataException {
      if (rawData.length < ICMPV6_PARAMETER_PROBLEM_HEADER_SIZE) {
        StringBuilder sb = new StringBuilder(80);
        sb.append("The data is too short to build an ICMPv6 Parameter Problem Header(")
          .append(ICMPV6_PARAMETER_PROBLEM_HEADER_SIZE)
          .append(" bytes). data: ")
          .append(ByteArrays.toHexString(rawData, " "));
        throw new IllegalRawDataException(sb.toString());
      }

      this.pointer = ByteArrays.getInt(rawData, POINTER_OFFSET);
    }

    private IcmpV6ParameterProblemHeader(Builder builder) {
      this.pointer = builder.pointer;
    }

    /**
     *
     * @return pointer
     */
    public int getPointer() { return pointer; }

    /**
     *
     * @return pointer
     */
    public long getPointerAsLong() { return pointer & 0xFFFFFFFFL; }

    @Override
    protected List<byte[]> getRawFields() {
      List<byte[]> rawFields = new ArrayList<byte[]>();
      rawFields.add(ByteArrays.toByteArray(pointer));
      return rawFields;
    }

    @Override
    public int length() {
      return ICMPV6_PARAMETER_PROBLEM_HEADER_SIZE;
    }

    @Override
    protected String buildString() {
      StringBuilder sb = new StringBuilder();
      String ls = System.getProperty("line.separator");

      sb.append("[ICMPv6 Parameter Problem Header (")
        .append(length())
        .append(" bytes)]")
        .append(ls);
      sb.append("  Pointer: ")
        .append(pointer)
        .append(ls);
      return sb.toString();
    }

  }

}