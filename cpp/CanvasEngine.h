#ifndef COSMIC_CANVASENGINE_H
#define COSMIC_CANVASENGINE_H

class CanvasEngine {

private:
    JNIEnv *_env;
    jobject _cvsView;
    jobject _cvs;

public:
    CanvasEngine() {
        _env = nullptr;
        _cvsView = nullptr;
        _cvs = nullptr;
    }

    CanvasEngine(JNIEnv *env, jobject cvsView, jobject cvs) {
        this->_env = env;
        this->_cvsView = cvsView;
        this->_cvs = cvs;
    }

    bool isValid() const {
        return (_env != nullptr && _cvsView != nullptr && _cvs != nullptr);
    }

    int getWidth() const {
        if (isValid()) {
            jclass canvas = _env->GetObjectClass(_cvs);
            jmethodID width = _env->GetMethodID(canvas, "getWidth", "()I");
            return _env->CallIntMethod(_cvs, width);
        }
        return 0;
    }

    int getHeight() const {
        if (isValid()) {
            jclass canvas = _env->GetObjectClass(_cvs);
            jmethodID width = _env->GetMethodID(canvas, "getHeight", "()I");
            return _env->CallIntMethod(_cvs, width);
        }
        return 0;
    }

    void DrawLine(Color color, float thickness, Vector2 start, Vector2 end) {
        if (isValid()) {
            jclass canvasView = _env->GetObjectClass(_cvsView);
            jmethodID drawline = _env->GetMethodID(canvasView, "DrawLine",
                                                   "(Landroid/graphics/Canvas;IIIIFFFFF)V");
            _env->CallVoidMethod(_cvsView, drawline, _cvs, (int) color.a, (int) color.r,
                                 (int) color.g, (int) color.b,
                                 thickness,
                                 start.X, start.Y, end.X, end.Y);
        }
    }

    void DrawRect(Color color, float thickness, Vector2 start, Vector2 end) {
        if (isValid()) {
            jclass canvasView = _env->GetObjectClass(_cvsView);
            jmethodID drawline = _env->GetMethodID(canvasView, "DrawRect",
                                                   "(Landroid/graphics/Canvas;IIIIFFFFF)V");
            _env->CallVoidMethod(_cvsView, drawline, _cvs, (int) color.a, (int) color.r,
                                 (int) color.g, (int) color.b,
                                 thickness,
                                 start.X, start.Y, end.X, end.Y);
        }
    }

    void DrawFilledRect(Color color, Vector2 start, Vector2 end) {
        if (isValid()) {
            jclass canvasView = _env->GetObjectClass(_cvsView);
            jmethodID drawline = _env->GetMethodID(canvasView, "DrawFilledRect",
                                                   "(Landroid/graphics/Canvas;IIIIFFFF)V");
            _env->CallVoidMethod(_cvsView, drawline, _cvs, (int) color.a, (int) color.r,
                                 (int) color.g, (int) color.b,
                                 start.X, start.Y, end.X, end.Y);
        }
    }

    void DrawText(Color color, const char *txt, Vector2 pos, float size) {
        if (isValid()) {
            jclass canvasView = _env->GetObjectClass(_cvsView);
            jmethodID drawtext = _env->GetMethodID(canvasView, "DrawText",
                                                   "(Landroid/graphics/Canvas;IIIILjava/lang/String;FFF)V");
            jstring s = _env->NewStringUTF(txt);
            _env->CallVoidMethod(_cvsView, drawtext, _cvs, (int) color.a, (int) color.r,
                                 (int) color.g, (int) color.b,
                                 s, pos.X, pos.Y, size);
            _env->DeleteLocalRef(s);
        }
    }

    void DrawInfo(Color color, const char *txt, Vector2 pos, float size) {
        if (isValid()) {
            jclass canvasView = _env->GetObjectClass(_cvsView);
            jmethodID drawtext = _env->GetMethodID(canvasView, "DrawInfo",
                                                   "(Landroid/graphics/Canvas;IIIILjava/lang/String;FFF)V");
            jstring s = _env->NewStringUTF(txt);
            _env->CallVoidMethod(_cvsView, drawtext, _cvs, (int) color.a, (int) color.r,
                                 (int) color.g, (int) color.b,
                                 s, pos.X, pos.Y, size);
            _env->DeleteLocalRef(s);
        }
    }

    void DrawItems(const char *txt, float distance, Vector2 pos, float size) {
        if (isValid()) {
            jclass canvasView = _env->GetObjectClass(_cvsView);
            jmethodID drawtext = _env->GetMethodID(canvasView, "DrawItems",
                                                   "(Landroid/graphics/Canvas;Ljava/lang/String;FFFF)V");
            jstring s = _env->NewStringUTF(txt);
            _env->CallVoidMethod(_cvsView, drawtext, _cvs,
                                 s, distance, pos.X, pos.Y, size);
            _env->DeleteLocalRef(s);
        }
    }

    void DrawVehicles(const char *txt, float distance, Vector2 pos, float size, int hp, int fuel,
                      bool isAim) {
        if (isValid()) {
            jclass canvasView = _env->GetObjectClass(_cvsView);
            jmethodID drawtext = _env->GetMethodID(canvasView, "DrawVehicles",
                                                   "(Landroid/graphics/Canvas;Ljava/lang/String;FFFFIIZ)V");
            jstring s = _env->NewStringUTF(txt);
            _env->CallVoidMethod(_cvsView, drawtext, _cvs,
                                 s, distance, pos.X, pos.Y, size, hp, fuel, isAim);
            _env->DeleteLocalRef(s);
        }
    }

    void DrawFilledCircle(Color color, Vector2 pos, float radius) {
        if (isValid()) {
            jclass canvasView = _env->GetObjectClass(_cvsView);
            jmethodID drawfilledcircle = _env->GetMethodID(canvasView, "DrawFilledCircle",
                                                           "(Landroid/graphics/Canvas;IIIIFFF)V");
            _env->CallVoidMethod(_cvsView, drawfilledcircle, _cvs, (int) color.a, (int) color.r,
                                 (int) color.g, (int) color.b, pos.X, pos.Y, radius);
        }
    }

    void DrawCircle(Color color, Vector2 pos, float radius, float thickness) {
        if (isValid()) {
            jclass canvasView = _env->GetObjectClass(_cvsView);
            jmethodID drawcircle = _env->GetMethodID(canvasView, "DrawCircle",
                                                     "(Landroid/graphics/Canvas;IIIIFFFF)V");
            _env->CallVoidMethod(_cvsView, drawcircle, _cvs, (int) color.a, (int) color.r,
                                 (int) color.g, (int) color.b, pos.X, pos.Y, radius, thickness);
        }
    }


    void DataPlayer(Color color, int weaponId, const char *dist, Vector2 pos, float size) {
        if (isValid()) {
            jclass canvasView = _env->GetObjectClass(_cvsView);
            jmethodID dataPlayer = _env->GetMethodID(canvasView, "DataPlayer",
                                                     "(Landroid/graphics/Canvas;IIIIILjava/lang/String;FFF)V");
            jstring distS = _env->NewStringUTF(dist);
            _env->CallVoidMethod(_cvsView, dataPlayer, _cvs, (int) color.a, (int) color.r,
                                 (int) color.g, (int) color.b,
                                 weaponId, distS, pos.X, pos.Y, size);
            _env->DeleteLocalRef(distS);
        }
    }

    void DrawWeapon(Color color, int wid, int ammo, Vector2 pos, float size) {
        if (isValid()) {
            jclass canvasView = _env->GetObjectClass(_cvsView);
            jmethodID drawtext = _env->GetMethodID(canvasView, "DrawWeapon",
                                                   "(Landroid/graphics/Canvas;IIIIIIFFF)V");
            _env->CallVoidMethod(_cvsView, drawtext, _cvs, (int) color.a, (int) color.r,
                                 (int) color.g, (int) color.b,
                                 wid, ammo, pos.X, pos.Y, size);
        }
    }

    void
    InfoPLayer(Color color, int teamId, const char *name, const char *flag, Vector2 pos,
               float size) {
        if (isValid()) {
            jclass canvasView = _env->GetObjectClass(_cvsView);
            jmethodID infoPLayer = _env->GetMethodID(canvasView, "InfoPLayer",
                                                     "(Landroid/graphics/Canvas;IIIIILjava/lang/String;Ljava/lang/String;FFF)V");
            jstring names = _env->NewStringUTF(name);
            jstring flags = _env->NewStringUTF(flag);
            _env->CallVoidMethod(_cvsView, infoPLayer, _cvs, (int) color.a, (int) color.r,
                                 (int) color.g, (int) color.b,
                                 teamId, names, flags, pos.X, pos.Y, size);
            _env->DeleteLocalRef(names);
            _env->DeleteLocalRef(flags);
        }
    }
};

#endif //COSMIC_CANVASENGINE_H
