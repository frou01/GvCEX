package handmadevehicle.entity.parts;

public interface IControlable{

    void setControl_RightClick(boolean value);
    void setControl_LeftClick(boolean value);
    void setControl_Space(boolean value);
    void setControl_x(boolean value);
    void setControl_w(boolean value);
    void setControl_a(boolean value);
    void setControl_s(boolean value);
    void setControl_d(boolean value);
    void setControl_f(boolean value);

    boolean getControl_RightClick();
    boolean getControl_LeftClick();
    boolean getControl_Space();
    boolean getControl_x();
    boolean getControl_w();
    boolean getControl_a();
    boolean getControl_s();
    boolean getControl_d();
    boolean getControl_f();
}
