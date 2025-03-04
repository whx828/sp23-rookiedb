package edu.berkeley.cs186.database.recovery.records;

import edu.berkeley.cs186.database.common.Buffer;
import edu.berkeley.cs186.database.common.ByteBuffer;
import edu.berkeley.cs186.database.recovery.LogRecord;
import edu.berkeley.cs186.database.recovery.LogType;

import java.util.Objects;
import java.util.Optional;

public class EndTransactionLogRecord extends LogRecord {
    private long transNum;
    private long prevLSN;

    public EndTransactionLogRecord(long transNum, long prevLSN) {
        super(LogType.END_TRANSACTION);
        this.transNum = transNum;
        this.prevLSN = prevLSN;
    }

    public static Optional<LogRecord> fromBytes(Buffer buf) {
        long transNum = buf.getLong();
        long prevLSN = buf.getLong();
        return Optional.of(new EndTransactionLogRecord(transNum, prevLSN));
    }

    @Override
    public Optional<Long> getTransNum() {
        return Optional.of(transNum);
    }

    @Override
    public Optional<Long> getPrevLSN() {
        return Optional.of(prevLSN);
    }

    @Override
    public byte[] toBytes() {
        byte[] b = new byte[1 + Long.BYTES + Long.BYTES];
        ByteBuffer.wrap(b)
            .put((byte) getType().getValue())
            .putLong(transNum)
            .putLong(prevLSN);
        return b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        EndTransactionLogRecord that = (EndTransactionLogRecord) o;
        return transNum == that.transNum &&
            prevLSN == that.prevLSN;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), transNum, prevLSN);
    }

    @Override
    public String toString() {
        return "EndTransactionLogRecord{" +
            "transNum=" + transNum +
            ", prevLSN=" + prevLSN +
            ", LSN=" + LSN +
            '}';
    }
}
