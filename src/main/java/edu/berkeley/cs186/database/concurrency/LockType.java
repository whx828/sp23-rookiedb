package edu.berkeley.cs186.database.concurrency;

/**
 * Utility methods to track the relationships between different lock types.
 */
public enum LockType {
    S,   // shared
    X,   // exclusive
    IS,  // intention shared
    IX,  // intention exclusive
    SIX, // shared intention exclusive
    NL;  // no lock held

    /**
     * This method checks whether lock types A and B are compatible with
     * each other. If a transaction can hold lock type A on a resource
     * at the same time another transaction holds lock type B on the same
     * resource, the lock types are compatible.
     */
    public static boolean compatible(LockType a, LockType b) {
        if (a == null || b == null) {
            throw new NullPointerException("null lock type");
        }
        // TODO(proj4_part1): implement
        switch (a) {
            case S:
                switch (b) {
                    case S:
                    case IS:
                    case NL:
                        return true;
                    case SIX:
                    case IX:
                    case X:
                        return false;
                    default:
                        throw new UnsupportedOperationException("bad lock type");
                }
            case X:
                switch (b) {
                    case NL:
                        return true;
                    case S:
                    case X:
                    case IS:
                    case IX:
                    case SIX:
                        return false;
                    default:
                        throw new UnsupportedOperationException("bad lock type");
                }
            case IS:
                switch (b) {
                    case S:
                    case IS:
                    case IX:
                    case SIX:
                    case NL:
                        return true;
                    case X:
                        return false;
                    default:
                        throw new UnsupportedOperationException("bad lock type");
                }
            case IX:
                switch (b) {
                    case IS:
                    case IX:
                    case NL:
                        return true;
                    case S:
                    case SIX:
                    case X:
                        return false;
                    default:
                        throw new UnsupportedOperationException("bad lock type");
                }
            case SIX:
                switch (b) {
                    case IS:
                    case NL:
                        return true;
                    case S:
                    case X:
                    case IX:
                    case SIX:
                        return false;
                    default:
                        throw new UnsupportedOperationException("bad lock type");
                }
            case NL:
                return true;
        }
        return false;
    }

    /**
     * This method returns the lock on the parent resource
     * that should be requested for a lock of type A to be granted.
     */
    public static LockType parentLock(LockType a) {
        if (a == null) {
            throw new NullPointerException("null lock type");
        }
        switch (a) {
            case S:
            case IS:
                return IS;
            case X:
            case IX:
            case SIX:
                return IX;
            case NL:
                return NL;
            default:
                throw new UnsupportedOperationException("bad lock type");
        }
    }

    /**
     * This method returns if parentLockType has permissions to grant a childLockType
     * on a child.
     */
    public static boolean canBeParentLock(LockType parentLockType, LockType childLockType) {
        if (parentLockType == null || childLockType == null) {
            throw new NullPointerException("null lock type");
        }
        // TODO(proj4_part1): implement
        switch (parentLockType) {
            case S:
            case X:
            case NL:
                switch (childLockType) {
                    case NL:
                        return true;
                    case S:
                    case X:
                    case IS:
                    case IX:
                    case SIX:
                        return false;
                    default:
                        throw new UnsupportedOperationException("bad lock type");
                }
            case IS:
                switch (childLockType) {
                    case S:
                    case IS:
                    case NL:
                        return true;
                    case X:
                    case IX:
                    case SIX:
                        return false;
                    default:
                        throw new UnsupportedOperationException("bad lock type");
                }
            case IX:
                switch (childLockType) {
                    case S:
                    case X:
                    case IS:
                    case IX:
                    case SIX:
                    case NL:
                        return true;
                    default:
                        throw new UnsupportedOperationException("bad lock type");
                }
            case SIX:
                switch (childLockType) {
                    case X:
                    case IX:
                    case SIX:
                    case NL:
                        return true;
                    case S:
                    case IS:
                        return false;
                    default:
                        throw new UnsupportedOperationException("bad lock type");
                }
            default:
                throw new UnsupportedOperationException("bad lock type");
        }
    }

    /**
     * This method returns whether a lock can be used for a situation
     * requiring another lock (e.g. an S lock can be substituted with
     * an X lock, because an X lock allows the transaction to do everything
     * the S lock allowed it to do).
     */
    public static boolean substitutable(LockType substitute, LockType required) {
        if (required == null || substitute == null) {
            throw new NullPointerException("null lock type");
        }
        // TODO(proj4_part1): implement
        switch (substitute) {
            case S:
                switch (required) {
                    case S:
                    case IS:
                    case NL:
                        return true;
                    default:
                        return false;
                }
            case X: return true;
            case IS:
                switch (required) {
                    case IS:
                    case NL:
                        return true;
                    default:
                        return false;
                }
            case IX:
                switch (required) {
                    case IS:
                    case IX:
                    case NL:
                        return true;
                    default:
                        return false;
                }
            case SIX:
                switch (required) {
                    case S:
                    case IS:
                    case IX:
                    case SIX:
                    case NL:
                        return true;
                    default:
                        return false;
                }
            case NL: return required == NL;
            default:
                throw new UnsupportedOperationException("bad lock type");
        }
    }

    /**
     * @return True if this lock is IX, IS, or SIX. False otherwise.
     */
    public boolean isIntent() {
        return this == LockType.IX || this == LockType.IS || this == LockType.SIX;
    }

    @Override
    public String toString() {
        switch (this) {
            case S:
                return "S";
            case X:
                return "X";
            case IS:
                return "IS";
            case IX:
                return "IX";
            case SIX:
                return "SIX";
            case NL:
                return "NL";
            default:
                throw new UnsupportedOperationException("bad lock type");
        }
    }
}
