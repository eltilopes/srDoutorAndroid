package br.com.srdoutorandroid.model;

/**
 * Created by elton on 02/02/2017.
 */


public class FragmentoFactory {

    public enum NavegacaoEnum {
        INICIO("role");

        private String role;
        private String label;

        private NavegacaoEnum( String role) {
            this.role = role;
        }
        public String getRole() { return role; }
        public String getLabel() { return label; }
/**
        INICIO(FragmentoDashboard.class, "Início", R.drawable.ic_inicio_black),
        CHAMADO_LISTA(FragmentoChamadoLista.class, "Chamados", R.drawable.ic_chamado_black, "chamado_read"),
        MENSAGEM_LISTA(FragmentoMensagensLista.class, "Mensagens", R.drawable.ic_mensagem_black, "mensagem_read"),
        LICENCA_LISTA(FragmentoLicencaMedicaLista.class, "Licença Médica", R.drawable.ic_licenca_black, "licenca_read"),
        WEB_MAIL(FragmentoWebMail.class, "WebMail", R.drawable.ic_contact_mail_black, "webmail_read"),
        FREQUENCIA_LISTA(FragmentoFrequenciaLista.class, "Infrequência", R.drawable.ic_infrequencia_black, "frequencia_read"),
        LOTACAO(FragmentoLotacao.class, "Lotação", R.drawable.ic_lotacao_black, "lotacao_read"),
        CONTRA_CHEQUE(FragmentoContraCheque.class, "Contra-Cheque", R.drawable.ic_contra_cheque_black, "contra_cheque_read"),
        PERFIL(FragmentoPerfilUsuario.class, "Perfil", R.drawable.ic_perfil_black),
        SAIR(Sair.class, "Sair", R.drawable.ic_sair_black),

        CHAMADO_LISTA_FILTRO(ChamadoListaFiltro.class, "Chamado", "chamado_read"),
        CHAMADO_FORMULARIO(FragmentoChamadoFormulario.class, "Chamados", "chamado_create"),
        CHAMADO_DETALHE(FragmentoChamadoDetalhe.class, "Chamados", "chamado_read"),
        FREQUENCIA_DETALHE(FragmentoFrequenciaDetalhe.class, "InFrequência", "frequencia_read"),
        MENSAGEM_FORMULARIO(FragmentoMensagemFormulario.class, "Mensagem", "mensagem_read");

        private Class<? extends Fragment> clazz;
        private String label;
        private String role;
        private int drawable;

        private NavegacaoEnum(Class<? extends  Fragment> klass, String label) {
            this.clazz = klass;
            this.label = label;
        }

        private NavegacaoEnum(Class<? extends  Fragment> klass, String label, String role) {
            this.clazz = klass;
            this.label = label;
            this.role = role;
        }

        private NavegacaoEnum(Class<? extends  Fragment> klass, String label, int drawable) {
            this.clazz = klass;
            this.label = label;
            this.drawable = drawable;
        }

        private NavegacaoEnum(Class<? extends  Fragment> klass, String label, int drawable, String role) {
            this.clazz = klass;
            this.label = label;
            this.role = role;
            this.drawable = drawable;
        }

        public String getLabel() { return label; }
        public String getRole() { return role; }
        public int getDrawable() { return drawable; }

        public static NavegacaoEnum findByLabel(String label){
            NavegacaoEnum retorno = null;
            for (NavegacaoEnum item : NavegacaoEnum.values())
                if(retorno == null && item.getLabel().equals(label))
                    retorno = item;
            return retorno;
        }

        public static NavegacaoEnum findByOrdinal(Integer ordinal){
            NavegacaoEnum retorno = null;
            for (NavegacaoEnum item : NavegacaoEnum.values())
                if(retorno == null && ordinal.equals(item.ordinal()))
                    retorno = item;
            return retorno;
        }

        public static List<NavegacaoEnum> getItensMenu() {
            return getItensMenu(new NavegacaoEnum[]{});
        }
        public static List<NavegacaoEnum> getItensMenu(NavegacaoEnum[] exception){
            List<NavegacaoEnum> retorno = new ArrayList<NavegacaoEnum>();
            boolean addItem = true;
            for (NavegacaoEnum item : NavegacaoEnum.values()) {
                if (addItem && Arrays.asList(exception).indexOf(item) < 0) {
                    retorno.add(item);
                    if (item.equals(NavegacaoEnum.SAIR)) addItem = false;
                }
            }
            return retorno;
        }

        public static Fragment getInstance(Fragment fragmentAtual, int position) {

            Fragment newInstance = null;
            try {

                Class<? extends Fragment> fragmentClazz = values()[position].clazz;

                if (!fragmentClazz.isInstance(fragmentAtual)) {
                    newInstance = fragmentClazz.newInstance();
                }
            } catch (final Exception e) {
//                e.printStackTrace();
                Log.e("Erro Instancia","Erro ao validar instancia.", e);
            }

            return newInstance;
        }
    }

    public static Fragment get(Fragment fragmentAtual, int position) {
        return NavegacaoEnum.getInstance(fragmentAtual, position);
    }
 */
    }
}
