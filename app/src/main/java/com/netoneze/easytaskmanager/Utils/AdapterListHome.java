package com.netoneze.easytaskmanager.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.netoneze.easytaskmanager.R;
import com.netoneze.easytaskmanager.modelo.Tarefa;

import java.util.HashMap;
import java.util.List;

public class AdapterListHome extends BaseExpandableListAdapter {
    private List<String> lstGrupos;
    private HashMap<String, List<Tarefa>> lstItensGrupos;
    private Context context;

    public AdapterListHome(Context context, List<String> grupos, HashMap<String, List<Tarefa>> itensGrupos) {
        // inicializa as variáveis da classe
        this.context = context;
        lstGrupos = grupos;
        lstItensGrupos = itensGrupos;
    }

    @Override
    public int getGroupCount() {
        // retorna a quantidade de grupos
        return lstGrupos.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // retorna a quantidade de itens de um grupo
        return lstItensGrupos.get(getGroup(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        // retorna um grupo
        return lstGrupos.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        // retorna um item do grupo
        return lstItensGrupos.get(getGroup(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        // retorna o id do grupo, porém como nesse exemplo
        // o grupo não possui um id específico, o retorno
        // será o próprio groupPosition
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // retorna o id do item do grupo, porém como nesse exemplo
        // o item do grupo não possui um id específico, o retorno
        // será o próprio childPosition
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        // retorna se os ids são específicos (únicos para cada
        // grupo ou item) ou relativos
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        // cria os itens principais (grupos)

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.grupo, null);
        }

        TextView tfTitulo = convertView.findViewById(R.id.tfTitulo);
        TextView tfData = convertView.findViewById(R.id.tfData);

        Tarefa tarefa = (Tarefa) getChild(groupPosition, 0);

        tfTitulo.setText((String) getGroup(groupPosition));
        tfData.setText(UtilsDate.formatDate(tarefa.getData()));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        // cria os subitens (itens dos grupos)

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_grupo, null);
        }

        TextView tfTitulo = convertView.findViewById(R.id.tfTituloCorpo);
        TextView tfData = convertView.findViewById(R.id.tfDataCorpo);
        TextView tfOnde = convertView.findViewById(R.id.tfOndeCorpo);
        TextView tfDescricao = convertView.findViewById(R.id.tfDescricaoCorpo);
        TextView tfPrioridade = convertView.findViewById(R.id.tfPrioridadeCorpo);
        TextView tfPeriodo = convertView.findViewById(R.id.tfPeriodoCorpo);

        Tarefa tarefa = (Tarefa) getChild(groupPosition, childPosition);
        tfTitulo.setText(tarefa.getTitulo());
        tfData.setText(UtilsDate.formatDate(tarefa.getData()));
        tfOnde.setText(tarefa.getLocal());
        tfDescricao.setText(tarefa.getDescricao());
        tfPrioridade.setText(tarefa.getPrioridade());
        tfPeriodo.setText(tarefa.getPeriodo());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // retorna se o subitem (item do grupo) é selecionável
        return true;
    }
}
