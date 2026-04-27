Public Class Form1

    Private opera As Operacion
    Private Sub btnCalcular_Click(sender As Object, e As EventArgs) Handles btnCalcular.Click

        Select Case True
            Case rbSuma.Checked
                opera = New Suma(tbValor1.Text, tbValor2.Text)

            Case rbDivision.Checked
                If tbValor2.Text <> 0 Then
                    opera = New Division()
                    opera.Valor1 = tbValor1.Text
                    opera.Valor2 = tbValor2.Text
                Else
                    MsgBox("No se permite la division por cero")
                End If

            Case rbMultiplicacion.Checked
                opera = New Multiplicacion()
                opera.Valor1 = tbValor1.Text
                opera.Valor2 = tbValor2.Text

            Case rbResta.Checked
                opera = New Resta()
                opera.Valor1 = tbValor1.Text
                opera.Valor2 = tbValor2.Text
            Case Else
        End Select

        tbResultado.Text = opera.Calcular()
    End Sub
End Class
