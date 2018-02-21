#include "BwaJni.h"
int main(int argc,char *argv[]);


JNIEXPORT void JNICALL Java_com_github_sparkbwa_BwaJni_runBwbble(JNIEnv *env, jobject obj, jobjectArray args)
{
	int stringCount = (*env)->GetArrayLength(env,args);
	char* params[stringCount];

    for (int i=0; i<stringCount; i++) {
        jstring string = (jstring) ((*env)->GetObjectArrayElement(env,args, i));
        const char *rawString = (*env)->GetStringUTFChars(env,string, 0);
        params[i] = (char*)rawString;
    }
	main(stringCount,params);
}
