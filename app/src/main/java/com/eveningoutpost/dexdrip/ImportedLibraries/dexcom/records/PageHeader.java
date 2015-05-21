package com.eveningoutpost.dexdrip.ImportedLibraries.dexcom.records;

import android.util.Log;

import com.eveningoutpost.dexdrip.ImportedLibraries.dexcom.CRC16;
import com.eveningoutpost.dexdrip.ImportedLibraries.dexcom.CRCFailRuntimeException;
import com.eveningoutpost.dexdrip.ImportedLibraries.dexcom.Constants;
import com.eveningoutpost.dexdrip.ImportedLibraries.dexcom.Utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

// This code and this particular library are from the NightScout android uploader
// Check them out here: https://github.com/nightscout/android-uploader
// Some of this code may have been modified for use in this project

public class PageHeader {
    private final int HEADER_SIZE=28;
    private final int FIRSTRECORDINDEX_OFFSET=0;
    private final int NUMRECS_OFFSET=4;
    private final int RECTYPE_OFFSET=8;
    private final int REV_OFFSET=9;
    private final int PAGENUMBER_OFFSET=10;
    private final int RESERVED2_OFFSET=14;
    private final int RESERVED3_OFFSET=18;
    private final int RESERVED4_OFFSET=22;

    private int firstRecordIndex;
    private int numOfRecords;
    private Constants.RECORD_TYPES recordType;
    private byte revision;
    private int pageNumber;
    private int reserved2;
    private int reserved3;
    private int reserved4;
    private byte[] crc=new byte[2];


    public PageHeader(byte[] packet) {
        Log.d("ShareTest", "Header Packet Data Length: " + packet.length);

        firstRecordIndex = ByteBuffer.wrap(packet).order(ByteOrder.LITTLE_ENDIAN).getInt(FIRSTRECORDINDEX_OFFSET);
        numOfRecords = ByteBuffer.wrap(packet).order(ByteOrder.LITTLE_ENDIAN).getInt(NUMRECS_OFFSET);
        recordType = Constants.RECORD_TYPES.values()[packet[RECTYPE_OFFSET]];
        revision = packet[REV_OFFSET];
        pageNumber = ByteBuffer.wrap(packet).order(ByteOrder.LITTLE_ENDIAN).getInt(PAGENUMBER_OFFSET);
        reserved2 = ByteBuffer.wrap(packet).order(ByteOrder.LITTLE_ENDIAN).getInt(RESERVED2_OFFSET);
        reserved3 = ByteBuffer.wrap(packet).order(ByteOrder.LITTLE_ENDIAN).getInt(RESERVED3_OFFSET);
        reserved4 = ByteBuffer.wrap(packet).order(ByteOrder.LITTLE_ENDIAN).getInt(RESERVED4_OFFSET);
        System.arraycopy(packet,HEADER_SIZE-Constants.CRC_LEN,crc,0,Constants.CRC_LEN);
        byte[] crc_calc = CRC16.calculate(packet,0,HEADER_SIZE - Constants.CRC_LEN);
        if (!Arrays.equals(this.crc, crc_calc)) {
            throw new CRCFailRuntimeException("CRC check failed: " + Utils.bytesToHex(this.crc) + " vs " + Utils.bytesToHex(crc_calc));
        }
    }

    public byte getRevision() {
        return revision;
    }

    public Constants.RECORD_TYPES getRecordType() {
        return recordType;
    }

    public int getFirstRecordIndex() {
        return firstRecordIndex;
    }

    public int getNumOfRecords() {
        return numOfRecords;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getReserved2() {
        return reserved2;
    }

    public int getReserved3() {
        return reserved3;
    }

    public int getReserved4() {
        return reserved4;
    }
}
