<Global.Microsoft.VisualBasic.CompilerServices.DesignerGenerated()>
Partial Class Form1
    Inherits System.Windows.Forms.Form

    'Form overrides dispose to clean up the component list.
    <System.Diagnostics.DebuggerNonUserCode()>
    Protected Overrides Sub Dispose(ByVal disposing As Boolean)
        Try
            If disposing AndAlso components IsNot Nothing Then
                components.Dispose()
            End If
        Finally
            MyBase.Dispose(disposing)
        End Try
    End Sub

    'Required by the Windows Form Designer
    Private components As System.ComponentModel.IContainer

    'NOTE: The following procedure is required by the Windows Form Designer
    'It can be modified using the Windows Form Designer.  
    'Do not modify it using the code editor.
    <System.Diagnostics.DebuggerStepThrough()>
    Private Sub InitializeComponent()
        Label1 = New Label()
        Label2 = New Label()
        Label3 = New Label()
        tbValor1 = New TextBox()
        tbValor2 = New TextBox()
        tbResultado = New TextBox()
        rbSuma = New RadioButton()
        rbResta = New RadioButton()
        rbMultiplicacion = New RadioButton()
        rbDivision = New RadioButton()
        btnCalcular = New Button()
        SuspendLayout()
        ' 
        ' Label1
        ' 
        Label1.AutoSize = True
        Label1.Location = New Point(80, 93)
        Label1.Name = "Label1"
        Label1.Size = New Size(55, 20)
        Label1.TabIndex = 0
        Label1.Text = "Valor 1"
        ' 
        ' Label2
        ' 
        Label2.AutoSize = True
        Label2.Location = New Point(80, 167)
        Label2.Name = "Label2"
        Label2.Size = New Size(55, 20)
        Label2.TabIndex = 1
        Label2.Text = "Valor 2"
        ' 
        ' Label3
        ' 
        Label3.AutoSize = True
        Label3.Location = New Point(80, 237)
        Label3.Name = "Label3"
        Label3.Size = New Size(75, 20)
        Label3.TabIndex = 2
        Label3.Text = "Resultado"
        ' 
        ' tbValor1
        ' 
        tbValor1.Location = New Point(139, 90)
        tbValor1.Name = "tbValor1"
        tbValor1.Size = New Size(125, 27)
        tbValor1.TabIndex = 3
        ' 
        ' tbValor2
        ' 
        tbValor2.Location = New Point(139, 164)
        tbValor2.Name = "tbValor2"
        tbValor2.Size = New Size(125, 27)
        tbValor2.TabIndex = 4
        ' 
        ' tbResultado
        ' 
        tbResultado.Font = New Font("Segoe UI", 13.8F, FontStyle.Regular, GraphicsUnit.Point)
        tbResultado.Location = New Point(161, 225)
        tbResultado.Name = "tbResultado"
        tbResultado.Size = New Size(158, 38)
        tbResultado.TabIndex = 5
        ' 
        ' rbSuma
        ' 
        rbSuma.AutoSize = True
        rbSuma.Checked = True
        rbSuma.Location = New Point(527, 87)
        rbSuma.Name = "rbSuma"
        rbSuma.Size = New Size(67, 24)
        rbSuma.TabIndex = 6
        rbSuma.TabStop = True
        rbSuma.Text = "Suma"
        rbSuma.UseVisualStyleBackColor = True
        ' 
        ' rbResta
        ' 
        rbResta.AutoSize = True
        rbResta.Location = New Point(527, 134)
        rbResta.Name = "rbResta"
        rbResta.Size = New Size(66, 24)
        rbResta.TabIndex = 6
        rbResta.Text = "Resta"
        rbResta.UseVisualStyleBackColor = True
        ' 
        ' rbMultiplicacion
        ' 
        rbMultiplicacion.AutoSize = True
        rbMultiplicacion.Location = New Point(527, 183)
        rbMultiplicacion.Name = "rbMultiplicacion"
        rbMultiplicacion.Size = New Size(124, 24)
        rbMultiplicacion.TabIndex = 6
        rbMultiplicacion.Text = "Multiplicacion"
        rbMultiplicacion.UseVisualStyleBackColor = True
        ' 
        ' rbDivision
        ' 
        rbDivision.AutoSize = True
        rbDivision.Location = New Point(527, 237)
        rbDivision.Name = "rbDivision"
        rbDivision.Size = New Size(83, 24)
        rbDivision.TabIndex = 6
        rbDivision.Text = "Division"
        rbDivision.UseVisualStyleBackColor = True
        ' 
        ' btnCalcular
        ' 
        btnCalcular.Location = New Point(380, 370)
        btnCalcular.Name = "btnCalcular"
        btnCalcular.Size = New Size(192, 45)
        btnCalcular.TabIndex = 7
        btnCalcular.Text = "Calcular"
        btnCalcular.UseVisualStyleBackColor = True
        ' 
        ' Form1
        ' 
        AutoScaleDimensions = New SizeF(8F, 20F)
        AutoScaleMode = AutoScaleMode.Font
        BackColor = Color.FromArgb(CByte(255), CByte(192), CByte(255))
        ClientSize = New Size(800, 450)
        Controls.Add(btnCalcular)
        Controls.Add(rbDivision)
        Controls.Add(rbMultiplicacion)
        Controls.Add(rbResta)
        Controls.Add(rbSuma)
        Controls.Add(tbResultado)
        Controls.Add(tbValor2)
        Controls.Add(tbValor1)
        Controls.Add(Label3)
        Controls.Add(Label2)
        Controls.Add(Label1)
        Name = "Form1"
        Text = "Calculadora"
        ResumeLayout(False)
        PerformLayout()
    End Sub

    Friend WithEvents Label1 As Label
    Friend WithEvents Label2 As Label
    Friend WithEvents Label3 As Label
    Friend WithEvents tbValor1 As TextBox
    Friend WithEvents tbValor2 As TextBox
    Friend WithEvents tbResultado As TextBox
    Friend WithEvents rbSuma As RadioButton
    Friend WithEvents rbResta As RadioButton
    Friend WithEvents rbMultiplicacion As RadioButton
    Friend WithEvents rbDivision As RadioButton
    Friend WithEvents btnCalcular As Button
End Class
