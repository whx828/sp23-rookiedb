package edu.berkeley.cs186.database.concurrency;

import edu.berkeley.cs186.database.TransactionContext;

/**
 * LockUtil is a declarative layer which simplifies multigranularity lock
 * acquisition for the user (you, in the last task of Part 2). Generally
 * speaking, you should use LockUtil for lock acquisition instead of calling
 * LockContext methods directly.
 */
public class LockUtil {
    /**
     * Ensure that the current transaction can perform actions requiring
     * `requestType` on `lockContext`.
     * <p>
     * `requestType` is guaranteed to be one of: S, X, NL.
     * <p>
     * This method should promote/escalate/acquire as needed, but should only
     * grant the least permissive set of locks needed. We recommend that you
     * think about what to do in each of the following cases:
     * - The current lock type can effectively substitute the requested type
     * - The current lock type is IX and the requested lock is S
     * - The current lock type is an intent lock
     * - None of the above: In this case, consider what values the explicit
     * lock type can be, and think about how ancestor looks will need to be
     * acquired or changed.
     * <p>
     * You may find it useful to create a helper method that ensures you have
     * the appropriate locks on all ancestors.
     */
    public static void ensureSufficientLockHeld(LockContext lockContext, LockType requestType) {
        // requestType must be S, X, or NL
        assert (requestType == LockType.S || requestType == LockType.X || requestType == LockType.NL);

        // Do nothing if the transaction or lockContext is null
        TransactionContext transaction = TransactionContext.getTransaction();
        if (transaction == null || lockContext == null) return;

        // You may find these variables useful
        LockContext parentContext = lockContext.parentContext();
        LockType effectiveLockType = lockContext.getEffectiveLockType(transaction);
        LockType explicitLockType = lockContext.getExplicitLockType(transaction);

        // TODO(proj4_part2): implement
        // case 1: The current lock type can effectively substitute the requested type
        if (LockType.substitutable(effectiveLockType, requestType)) return;

        // case 2: The current lock type is IX and the requested lock is S
        if (explicitLockType == LockType.IX && requestType == LockType.S) {
            lockContext.promote(transaction, LockType.SIX);
            return;
        }

        // case 3: The current lock type is an intent lock
        if (explicitLockType.isIntent()) {
            lockContext.escalate(transaction);
            explicitLockType = lockContext.getExplicitLockType(transaction);
            if (explicitLockType == requestType || explicitLockType == LockType.X) return;
        }

        // case 4: None of the above (now explicit lock type can only be S or NL)
        // more specifically, the (explicit lock type, request type) pair can only be (NL, S), (NL, X), (S, X)
        if (requestType == LockType.S) {
            // 尝试在当前资源的祖先 context 上获得 IS，为了尽可能小的(意向)锁
            enforceLock(transaction, parentContext, LockType.IS);
        } else {
            // 尝试在当前资源的祖先 context 上获得 IX，为了尽可能小的(意向)锁
            enforceLock(transaction, parentContext, LockType.IX);
        }

        if (explicitLockType == LockType.NL) {
            // (NL, S) -> S or (NL, X) -> X
            // 祖先处理完毕，获得 S or X
            lockContext.acquire(transaction, requestType);
        } else {
            // (S, X) -> X
            // 祖先处理完毕，从 S 升级到 X
            lockContext.promote(transaction, requestType);
        }
    }

    // TODO(proj4_part2) add any helper methods you want
    // 递归地在所有父级 context 上强制获得 IS 或 IX
    private static void enforceLock(TransactionContext transaction, LockContext lockContext, LockType lockType) {
        assert (lockType == LockType.IS || lockType == LockType.IX);
        if (lockContext == null) return;

        enforceLock(transaction, lockContext.parentContext(), lockType);

        // 对于 NL 或者 S 的父级，currLockType 只可能是 S/IS/IX/SIX/NL 或者 S/IS
        // 祖先中如有 X，会在 case 1 就返回，不会进入 enforceLock
        LockType currLockType = lockContext.getEffectiveLockType(transaction);
        if (!LockType.substitutable(currLockType, lockType)) { // true: S/IS/IX/SIX, IS
            if (currLockType == LockType.NL) {
                // NL -> IS/IX
                lockContext.acquire(transaction, lockType);
            } else {
                // S/IS -> IX
                lockContext.promote(transaction, lockType);
            }
        }
    }
}
