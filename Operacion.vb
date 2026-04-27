Public MustInherit Class Operacion
    Property Valor1 As Single
    Property Valor2 As Single

    Public Sub New()
    End Sub

    Public Sub New(valor1 As Single, valor2 As Single)
        Me.Valor1 = valor1
        Me.Valor2 = valor2
    End Sub

    Public MustOverride Function Calcular() As Single


End Class
