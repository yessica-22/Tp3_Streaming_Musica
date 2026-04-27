Public Class Suma
    Inherits Operacion

    Public Sub New(valor1 As Single, valor2 As Single)
        MyBase.New(valor1, valor2) 'Equibale a SUPER en Java
    End Sub
    Public Overrides Function Calcular() As Single
        Return Valor1 + Valor2
    End Function
End Class
