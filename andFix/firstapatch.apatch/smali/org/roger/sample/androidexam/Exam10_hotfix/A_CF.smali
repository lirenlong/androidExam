.class public Lorg/roger/sample/androidexam/Exam10_hotfix/A_CF;
.super Ljava/lang/Object;
.source "A.java"


# static fields
.field static i:I

.field private static o:Lorg/roger/sample/androidexam/Exam10_hotfix/O;


# instance fields
.field s:Ljava/lang/String;


# direct methods
.method static constructor <clinit>()V
    .locals 2

    .prologue
    .line 10
    new-instance v0, Lorg/roger/sample/androidexam/Exam10_hotfix/O;

    const-string v1, "a"

    invoke-direct {v0, v1}, Lorg/roger/sample/androidexam/Exam10_hotfix/O;-><init>(Ljava/lang/String;)V

    sput-object v0, Lorg/roger/sample/androidexam/Exam10_hotfix/A_CF;->o:Lorg/roger/sample/androidexam/Exam10_hotfix/O;

    .line 11
    const/16 v0, 0xa

    sput v0, Lorg/roger/sample/androidexam/Exam10_hotfix/A_CF;->i:I

    return-void
.end method

.method public constructor <init>()V
    .locals 1

    .prologue
    .line 8
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 9
    const-string v0, "s"

    iput-object v0, p0, Lorg/roger/sample/androidexam/Exam10_hotfix/A_CF;->s:Ljava/lang/String;

    return-void
.end method

.method public static a(Ljava/lang/String;)Ljava/lang/String;
    .locals 2
    .param p0, "str"    # Ljava/lang/String;
    .annotation runtime Lcom/alipay/euler/andfix/annotation/MethodReplace;
        clazz = "org.roger.sample.androidexam.Exam10_hotfix.A"
        method = "a"
    .end annotation

    .prologue
    .line 14
    const-string v0, "euler"

    const-string v1, "fix error"

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 15
    const-string v0, "a -- hacked"

    return-object v0
.end method


# virtual methods
.method public b(Ljava/lang/String;Ljava/lang/String;)I
    .locals 2
    .param p1, "s1"    # Ljava/lang/String;
    .param p2, "s2"    # Ljava/lang/String;

    .prologue
    .line 19
    const-string v0, "euler"

    const-string v1, "fix error"

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 20
    const-string v0, "euler"

    sget-object v1, Lorg/roger/sample/androidexam/Exam10_hotfix/A;->o:Lorg/roger/sample/androidexam/Exam10_hotfix/O;

    iget-object v1, v1, Lorg/roger/sample/androidexam/Exam10_hotfix/O;->s:Ljava/lang/String;

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 21
    const/4 v0, 0x0

    return v0
.end method

.method public getI()I
    .locals 1

    .prologue
    .line 25
    sget v0, Lorg/roger/sample/androidexam/Exam10_hotfix/A;->i:I

    return v0
.end method
