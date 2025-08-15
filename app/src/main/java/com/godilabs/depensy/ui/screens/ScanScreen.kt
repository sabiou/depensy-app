package com.godilabs.depensy.ui.screens

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FlashOff
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import java.io.File

@Composable
fun ScanScreen(modifier: Modifier = Modifier, onBack: () -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        )
    }
    val previewView = remember { PreviewView(context) }
    var camera by remember { mutableStateOf<Camera?>(null) }
    var isFlashOn by remember { mutableStateOf(false) }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasCameraPermission = isGranted
    }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        if (hasCameraPermission) {
            val imageCapture = remember { ImageCapture.Builder().build() }

            LaunchedEffect(previewView) {
                val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()
                    val preview = Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }
                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                    try {
                        cameraProvider.unbindAll()
                        camera = cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            cameraSelector,
                            preview,
                            imageCapture
                        )
                    } catch (e: Exception) {
                        Log.e("ScanScreen", "Camera binding failed", e)
                    }
                }, ContextCompat.getMainExecutor(context))
            }

            AndroidView({ previewView }, modifier = Modifier.fillMaxSize())

            // Scan Zone Overlay
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                val canvasWidth = size.width
                val canvasHeight = size.height

                // Semi-transparent overlay
                drawRect(
                    color = Color.Black.copy(alpha = 0.5f),
                    size = size
                )

                // Clear scan area (square in center)
                val scanSize = 350.dp.toPx()
                val left = (canvasWidth - scanSize) / 2
                val top = (canvasHeight - scanSize) / 2

                // Clear the scan area
                drawRect(
                    color = Color.Transparent,
                    topLeft = androidx.compose.ui.geometry.Offset(left, top),
                    size = androidx.compose.ui.geometry.Size(scanSize, scanSize),
                    blendMode = androidx.compose.ui.graphics.BlendMode.Clear
                )

                // Draw scan border
                drawRect(
                    color = Color.White,
                    topLeft = androidx.compose.ui.geometry.Offset(left, top),
                    size = androidx.compose.ui.geometry.Size(scanSize, scanSize),
                    style = Stroke(
                        width = 3.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 0f)
                    )
                )

                // Corner indicators
                val cornerLength = 30.dp.toPx()
                val cornerWidth = 4.dp.toPx()

                // Top-left corner
                drawLine(
                    color = Color.White,
                    start = androidx.compose.ui.geometry.Offset(left, top),
                    end = androidx.compose.ui.geometry.Offset(left + cornerLength, top),
                    strokeWidth = cornerWidth
                )
                drawLine(
                    color = Color.White,
                    start = androidx.compose.ui.geometry.Offset(left, top),
                    end = androidx.compose.ui.geometry.Offset(left, top + cornerLength),
                    strokeWidth = cornerWidth
                )

                // Top-right corner
                drawLine(
                    color = Color.White,
                    start = androidx.compose.ui.geometry.Offset(left + scanSize, top),
                    end = androidx.compose.ui.geometry.Offset(left + scanSize - cornerLength, top),
                    strokeWidth = cornerWidth
                )
                drawLine(
                    color = Color.White,
                    start = androidx.compose.ui.geometry.Offset(left + scanSize, top),
                    end = androidx.compose.ui.geometry.Offset(left + scanSize, top + cornerLength),
                    strokeWidth = cornerWidth
                )

                // Bottom-left corner
                drawLine(
                    color = Color.White,
                    start = androidx.compose.ui.geometry.Offset(left, top + scanSize),
                    end = androidx.compose.ui.geometry.Offset(left + cornerLength, top + scanSize),
                    strokeWidth = cornerWidth
                )
                drawLine(
                    color = Color.White,
                    start = androidx.compose.ui.geometry.Offset(left, top + scanSize),
                    end = androidx.compose.ui.geometry.Offset(left, top + scanSize - cornerLength),
                    strokeWidth = cornerWidth
                )

                // Bottom-right corner
                drawLine(
                    color = Color.White,
                    start = androidx.compose.ui.geometry.Offset(left + scanSize, top + scanSize),
                    end = androidx.compose.ui.geometry.Offset(
                        left + scanSize - cornerLength,
                        top + scanSize
                    ),
                    strokeWidth = cornerWidth
                )
                drawLine(
                    color = Color.White,
                    start = androidx.compose.ui.geometry.Offset(left + scanSize, top + scanSize),
                    end = androidx.compose.ui.geometry.Offset(
                        left + scanSize,
                        top + scanSize - cornerLength
                    ),
                    strokeWidth = cornerWidth
                )
            }

            // Top Controls
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                // Close button
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .background(
                            Color.Black.copy(alpha = 0.5f),
                            CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Fermer",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                // Flashlight toggle
                IconButton(
                    onClick = {
                        isFlashOn = !isFlashOn
                        camera?.cameraControl?.enableTorch(isFlashOn)
                    },
                    modifier = Modifier
                        .background(
                            Color.Black.copy(alpha = 0.5f),
                            CircleShape
                        )
                ) {
                    Icon(
                        imageVector = if (isFlashOn) Icons.Default.FlashOn else Icons.Default.FlashOff,
                        contentDescription = if (isFlashOn) "Éteindre la lampe" else "Allumer la lampe",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // Bottom controls
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 40.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Placez le reçu dans la zone de scan",
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .background(
                                Color.Black.copy(alpha = 0.7f),
                                RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )

                    Spacer(Modifier.height(24.dp))

                    // Take picture button
                    IconButton(
                        onClick = {
                            val outputFilePath =
                                "${context.externalMediaDirs.first().absolutePath}/image.jpg"
                            val file = File(outputFilePath)
                            val outputOptions = ImageCapture
                                .OutputFileOptions
                                .Builder(file)
                                .build()

                            imageCapture.takePicture(
                                outputOptions,
                                ContextCompat.getMainExecutor(context),
                                object : ImageCapture.OnImageSavedCallback {
                                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                                        Log.d("ScanScreen", "Image saved: ${file.absolutePath}")
                                    }

                                    override fun onError(exception: ImageCaptureException) {
                                        Log.e("ScanScreen", "Image capture failed", exception)
                                    }
                                }
                            )
                        },
                        modifier = Modifier
                            .size(72.dp)
                            .background(Color.White, CircleShape)
                            .border(4.dp, Color.Black, CircleShape)
                    ) {
                        // This is an empty button, the design is in the modifier
                    }
                }
            }
        } else {
            // Permission denied state
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Accès à la caméra requis",
                    color = Color.White,
                    fontSize = 20.sp
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Autorisez l'accès à la caméra pour scanner les reçus",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 16.sp
                )
                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = {
                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    )
                ) {
                    Text("Autoriser l'accès")
                }
            }
        }
    }
}