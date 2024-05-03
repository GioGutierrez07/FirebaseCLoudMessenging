package com.example.firebasecloudmessenging

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.example.firebasecloudmessenging.ui.theme.FirebaseCLoudMessengingTheme
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        preguntarPermisoNotificacion()
        tokenNuevo()



        setContent {
            FirebaseCLoudMessengingTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
    //funcion que que pide permisos para recibir notificaciones
    private fun preguntarPermisoNotificacion(){
        //si el sdk es mayor o igual a la version13
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)== PackageManager.PERMISSION_GRANTED){

            }else{

                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }


        }
    }

    ///recuperamos la respuesta del usuario  de los permisos
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ){isGrented:Boolean->
        if(isGrented){

        }else{

        }

    }

    //funcion que recupera el token unico del dispositivo
    private fun tokenNuevo(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener {
            if(!it.isSuccessful){
                Log.w("FCM TOKEN","Fallo al registrar el token")
                return@OnCompleteListener

            }
            //asignamos el resultado del token  a nuestra  variable token
            val token=it.result
            Log.d("FCM TOKEN", token.toString())
        })

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        color = Color.Black,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FirebaseCLoudMessengingTheme {
        Greeting("Android")
    }
}