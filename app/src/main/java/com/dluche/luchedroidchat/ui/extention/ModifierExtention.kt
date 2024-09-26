package com.dluche.luchedroidchat.ui.extention

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.unit.Dp

fun Modifier.bottomBorder(color: Color, strokeWidth: Dp) = this.drawBehind {
    val strokeWidthPx = strokeWidth.toPx()

    val width = size.width
    val height = size.height - strokeWidthPx / 2

    //Desenha no canvas
    drawLine(
        color = color,
        start = Offset(x = 0f, y = height),
        end = Offset(x = width, y = height),
        strokeWidth = strokeWidthPx
    )

}

/**
 * Modificador custom sem composição de modificadores existentes
 * IMPORTANTE: Sempre dar preferencia a criar modificadores usando a composição de modifadores existentes
 * os modificadores com node e elemente devem ser usados somente quando não for possível usar
 * a composição de modificadores existentes
 * https://developer.android.com/develop/ui/compose/custom-modifiers?hl=pt-br#implement-custom
 */
//Passo 1 - Criar o nó que efetivamente é o comportamento do modificador
//Implementação de Modifier customizado usando o Modifier.node
//Ainda necessario criar o elemente
private class BottomBorderNode(
    var color:Color,
    var strokeWidth:Dp
): DrawModifierNode, Modifier.Node(){

    override fun ContentDrawScope.draw() {
        val strokeWidthPx = strokeWidth.toPx()

        val width = size.width
        val height = size.height - strokeWidthPx / 2

        //Desenha no canvas
        drawLine(
            color = color,
            start = Offset(x = 0f, y = height),
            end = Offset(x = width, y = height),
            strokeWidth = strokeWidthPx
        )
    }


}

//Passo 2 - Criar o element que gerencia a instancia e atualização do nó na composição e recomposições

private data class BottomBorderElement(
    val color:Color,
    val strokeWidth:Dp
) : ModifierNodeElement<BottomBorderNode>(){

    //Cria o nó na primeira composição
    override fun create(): BottomBorderNode {
        return BottomBorderNode(color, strokeWidth)
    }

    //Atualiza o nó existente na recomposição
    override fun update(node: BottomBorderNode) {
        node.color = color
        node.strokeWidth = strokeWidth
    }

    //Orienta o debug do compose a entender o que esse modificador faz e suas proprieades
    override fun InspectorInfo.inspectableProperties() {
        name = "bottomBorder"
        properties["color"] = color
        properties["strokeWidth"] = strokeWidth

    }

}

//Passo 3: Criar a extensão de modifier adicionando o element criado no modificador atual.
//fun infix then para adicionar o element ao modificador.
fun Modifier.bottomBorder2(color: Color, strokeWidth: Dp) =
    this then BottomBorderElement(color, strokeWidth)

