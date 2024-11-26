package com.migi.bag_a_momement.rawdepth;

public class BridgeActivity extends FlutterActivity {
    // Channel name
    private static final String CHANNEL = "com.example.example/message";

    // Result variable
    private MethodChannel.Result myResult;

    // Request code
    private static final int REQUEST_CODE = 1234;

    // Invoked method
    private void getMessageAndroid() {
        Intent intent = new Intent(this, ArActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    // Configure flutter engine
    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);

        // Method channel
        new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL)
                .setMethodCallHandler(
                        (call, result) -> {
                            myResult = result;

                            // Invoked method handling
                            if (call.method.equals("getMessageAndroid")) {
                                try {
                                    getMessageAndroid();
                                } catch (Exception e) {
                                    myResult.error("Unavailable", "Opennig SecondActivity is not available", null);
                                }
                            } else {
                                myResult.notImplemented();
                            }
                        }
                );
    }

    // Activity result from invoked method
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Return flutter-side message
                myResult.success(data.getData().toString());
            } else {
                myResult.error("Unavailable", "Opennig SecondActivity is not available", null);
            }
        }
    }
}
