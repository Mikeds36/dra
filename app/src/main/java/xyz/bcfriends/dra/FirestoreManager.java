package xyz.bcfriends.dra;

import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FirestoreManager {
    private static final String TAG = "FirestoreManager";

    private final FirebaseAuth mAuth;
    private final FirebaseUser mUser;
    private final FirebaseFirestore mDatabase;

    public FirestoreManager() {
        this.mAuth = FirebaseAuth.getInstance();
        this.mUser = mAuth.getCurrentUser();
        this.mDatabase = FirebaseFirestore.getInstance();
    }

    void testWriteData() {
        Map<String, Object> data = new HashMap<>();
        data.put("depressStatus", 5);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        String today = format.format(new Date());

        mDatabase.collection("users")
                .document(mUser.getUid())
                .collection("Records")
                .document(today)
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    void FSWriteData(int depressStatus) {
        Map<String, Object> data = new HashMap<>();
        data.put("depressStatus", depressStatus);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        String today = format.format(new Date());

        mDatabase.collection("users")
            .document(mUser.getUid())
            .collection("Records")
            .document(today)
            .set(data)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "DocumentSnapshot successfully written!");
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "Error writing document", e);
                }
            });
    }

}
