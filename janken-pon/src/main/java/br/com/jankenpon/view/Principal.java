package br.com.jankenpon.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import br.com.jankenpon.controller.Partida;
import br.com.jankenpon.enums.Sinal;
import br.com.jankenpon.pojo.Jogador;

public class Principal extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final Integer QTDE_VITORIA_PARA_VENCER = 3;

	private Sinal sinalUm;
	private Sinal sinalDois;

	private Jogador jogadorUm;
	private Jogador jogadorDois;

	private BufferedImage bufferedImageJankenpon;

	private Partida partida;

	private JPanel contentPanel;
	private JPanel headerPanel;
	private JPanel bodyPanel;
	private JPanel inicioPanel;
	private JPanel jogarPanel;
	private JPanel rightPanel;
	private JPanel regraPanel;
	private JTextField textJogadorUm;
	private JTextField textJogadorDois;
	private JButton btnConfirmarUm;
	private JButton btnConfirmarDois;
	private JButton btnSpock;
	private JButton btnLagarto;
	private JButton btnPedra;
	private JButton btnPapel;
	private JButton btnTesoura;
	private JButton btnVoltar;
	private JLabel imagemHeader;
	private JLabel lblMensagem;
	private JLabel lblJogadorUm;
	private JLabel lblJogadorDois;
	private JLabel lblVitoriasUm;
	private JLabel lblDerrotasUm;
	private JLabel lblEmpatesUm;
	private JLabel lblVitoriasDois;
	private JLabel lblDerrotasDois;
	private JLabel lblEmpatesDois;
	private JLabel lblRodada;
	private JPanel sobrePanel;
	private JButton btnSobre;
	private JButton btnVoltaSobre;
	private JLabel labelSobre;
	private JLabel lblJogoSimplesDa;
	private JLabel lblImagemSobre;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void renderizarPlacar(boolean renderizar) {
		lblVitoriasUm.setVisible(renderizar);
		lblDerrotasUm.setVisible(renderizar);
		lblEmpatesUm.setVisible(renderizar);

		lblVitoriasDois.setVisible(renderizar);
		lblDerrotasDois.setVisible(renderizar);
		lblEmpatesDois.setVisible(renderizar);

		lblRodada.setVisible(renderizar);
	}

	private void atualizarPlacar() {
		lblVitoriasUm.setText("Vitorias: " + jogadorUm.getQtdeVitoria());
		lblDerrotasUm.setText("Derrotas: " + jogadorUm.getQtdeDerrota());
		lblEmpatesUm.setText("Empates: " + jogadorUm.getQtdeEmpate());

		lblVitoriasDois.setText("Vitorias: " + jogadorDois.getQtdeVitoria());
		lblDerrotasDois.setText("Derrotas: " + jogadorDois.getQtdeDerrota());
		lblEmpatesDois.setText("Empates: " + jogadorDois.getQtdeEmpate());

		lblRodada.setText("Rodada " + (partida.getQtdeRodada() + 1));
	}

	private void renderizarJogadores(boolean renderizar) {
		lblJogadorUm.setVisible(renderizar);
		lblJogadorDois.setVisible(renderizar);

		textJogadorUm.setVisible(renderizar);
		textJogadorDois.setVisible(renderizar);

		btnConfirmarUm.setVisible(renderizar);
		btnConfirmarDois.setVisible(renderizar);
	}

	private void renderizarBtnJogadas(boolean renderizar) {
		btnLagarto.setVisible(renderizar);
		btnPapel.setVisible(renderizar);
		btnPedra.setVisible(renderizar);
		btnSpock.setVisible(renderizar);
		btnTesoura.setVisible(renderizar);
	}

	private void habilitarJogadorUm(boolean habilitar) {
		textJogadorUm.setEnabled(habilitar);
		btnConfirmarUm.setEnabled(habilitar);
	}

	private void habilitarJogadorDois(boolean habilitar) {
		textJogadorDois.setEnabled(habilitar);
		btnConfirmarDois.setEnabled(habilitar);
	}

	private void jogar(Sinal sinal) {
		if (quemJoga() == 1) {
			sinalUm = sinal;

			mensagemQuemJoga();

		} else {
			sinalDois = sinal;

			novaRodada();
		}
	}

	private void novaRodada() {
		partida.adicionarRodada(sinalUm, sinalDois);

		sinalUm = null;
		sinalDois = null;

		atualizarPlacar();

		mostrarMensagem(partida.getResultado());

		Jogador vencedor = verificarVencedor();

		if (vencedor != null) {
			verificarNovaPartida(vencedor);
		} else {
			mensagemQuemJoga();
		}
	}

	private Jogador verificarVencedor() {
		Jogador vencedor = null;
		if (jogadorUm.getQtdeVitoria() == QTDE_VITORIA_PARA_VENCER) {
			vencedor = jogadorUm;
		} else if (jogadorDois.getQtdeVitoria() == QTDE_VITORIA_PARA_VENCER) {
			vencedor = jogadorDois;
		}
		return vencedor;
	}

	private void verificarNovaPartida(Jogador vencedor) {
		boolean novaPartida = mostrarMensagemConfirmacao("Fim da partida!",
				"O jogador " + vencedor.getNome() + " venceu!\nDeseja jogar outra partida?");

		if (novaPartida) {
			partida = new Partida();

			jogadorUm = new Jogador();
			jogadorDois = new Jogador();

			textJogadorUm.setText("");
			textJogadorDois.setText("");

			mensagemJogo("Jogador 1 - Insira seu nome!");

			habilitarJogadorUm(true);

			renderizarBtnJogadas(false);

			renderizarPlacar(false);
		} else {
			System.exit(0);
		}
	}

	private void mensagemQuemJoga() {
		if (quemJoga() == 1) {
			mensagemJogo(jogadorUm.getNome() + " - É a sua vez!");
		} else {
			mensagemJogo(jogadorDois.getNome() + " - É a sua vez!");
		}
	}

	private Integer quemJoga() {
		if (sinalUm == null) {
			return 1;
		} else {
			return 2;
		}
	}

	private boolean mostrarMensagemConfirmacao(String titulo, String mensagem) {
		return JOptionPane.showConfirmDialog(null, mensagem, titulo, JOptionPane.YES_NO_OPTION) == 0;

	}

	private void mensagemJogo(String mensagem) {
		lblMensagem.setText(mensagem);
	}

	private void mostrarMensagem(String mensagem) {
		JOptionPane.showMessageDialog(null, mensagem);
	}

	private Image getImagemHeader() {
		return new ImageIcon(getClass().getClassLoader().getResource("jankenpon1.png")).getImage()
				.getScaledInstance(600, 140, Image.SCALE_DEFAULT);
	}
	
	private Image getImagemSobre() {
		return new ImageIcon(getClass().getClassLoader().getResource("jankenpon3.jpg")).getImage()
				.getScaledInstance(285, 156, Image.SCALE_DEFAULT);
	}

	private void carregarImagemBtn() {
		try {
			bufferedImageJankenpon = ImageIO.read(getClass().getClassLoader().getResource("jankenpon2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Principal() {
		setTitle("JankenPon");
		partida = new Partida();

		carregarImagemBtn();

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 639, 543);
		contentPanel = new JPanel();
		contentPanel.setBackground(Color.LIGHT_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanel);
		contentPanel.setLayout(null);

		headerPanel = new JPanel();
		headerPanel.setBackground(Color.WHITE);
		headerPanel.setBounds(10, 11, 600, 140);
		contentPanel.add(headerPanel);
		headerPanel.setLayout(null);

		imagemHeader = new JLabel("");
		imagemHeader.setIcon(new ImageIcon(getImagemHeader()));
		imagemHeader.setBounds(0, 0, 600, 140);

		headerPanel.add(imagemHeader);

		bodyPanel = new JPanel();
		bodyPanel.setBackground(Color.WHITE);
		bodyPanel.setBounds(157, 162, 305, 331);
		contentPanel.add(bodyPanel);
		bodyPanel.setLayout(new CardLayout(0, 0));

		inicioPanel = new JPanel();
		inicioPanel.setBackground(Color.WHITE);
		bodyPanel.add(inicioPanel, "name_601080338006831");
		inicioPanel.setLayout(null);

		JButton btnJogar = new JButton("Jogar");
		btnJogar.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnJogar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mensagemJogo("Jogador 1 - Insira seu nome!");
				renderizarJogadores(true);
				habilitarJogadorDois(false);

				bodyPanel.removeAll();
				bodyPanel.add(jogarPanel);
				bodyPanel.repaint();
				bodyPanel.revalidate();
			}
		});
		btnJogar.setBounds(10, 31, 285, 64);
		inicioPanel.add(btnJogar);

		JButton btnRegra = new JButton("Regras");
		btnRegra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				bodyPanel.removeAll();
				bodyPanel.add(regraPanel);
				bodyPanel.repaint();
				bodyPanel.revalidate();
			}
		});
		btnRegra.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnRegra.setBounds(10, 106, 285, 64);
		inicioPanel.add(btnRegra);

		btnSobre = new JButton("Sobre");
		btnSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bodyPanel.removeAll();
				bodyPanel.add(sobrePanel);
				bodyPanel.repaint();
				bodyPanel.revalidate();
			}
		});
		btnSobre.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnSobre.setBounds(10, 183, 285, 64);
		inicioPanel.add(btnSobre);

		jogarPanel = new JPanel();
		jogarPanel.setBackground(Color.WHITE);
		bodyPanel.add(jogarPanel, "name_601097070121218");
		jogarPanel.setLayout(null);

		lblMensagem = new JLabel("");
		lblMensagem.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMensagem.setBounds(10, 11, 285, 22);
		jogarPanel.add(lblMensagem);

		btnSpock = new JButton("");
		btnSpock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jogar(Sinal.SPOCK);
			}
		});
		btnSpock.setBackground(Color.WHITE);
		btnSpock.setBorder(BorderFactory.createEmptyBorder());
		btnSpock.setIcon(new ImageIcon(
				bufferedImageJankenpon.getSubimage(7, 85, 102, 102).getScaledInstance(65, 65, Image.SCALE_DEFAULT)));

		btnPedra = new JButton("");
		btnPedra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jogar(Sinal.PEDRA);
			}
		});

		lblRodada = new JLabel("");
		lblRodada.setHorizontalAlignment(SwingConstants.CENTER);
		lblRodada.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRodada.setBounds(10, 38, 285, 22);
		jogarPanel.add(lblRodada);
		btnPedra.setBackground(Color.WHITE);
		btnPedra.setBorder(BorderFactory.createEmptyBorder());
		btnPedra.setIcon(new ImageIcon(
				bufferedImageJankenpon.getSubimage(102, 5, 102, 102).getScaledInstance(65, 65, Image.SCALE_DEFAULT)));

		btnPedra.setBounds(66, 150, 65, 65);
		jogarPanel.add(btnPedra);
		btnSpock.setBounds(76, 226, 65, 65);
		jogarPanel.add(btnSpock);

		btnLagarto = new JButton("");
		btnLagarto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jogar(Sinal.LAGARTO);
			}
		});
		btnLagarto.setBackground(Color.WHITE);
		btnLagarto.setBorder(BorderFactory.createEmptyBorder());
		btnLagarto.setIcon(new ImageIcon(
				bufferedImageJankenpon.getSubimage(41, 190, 102, 102).getScaledInstance(65, 65, Image.SCALE_DEFAULT)));

		btnLagarto.setBounds(177, 150, 65, 65);
		jogarPanel.add(btnLagarto);

		btnPapel = new JButton("");
		btnPapel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jogar(Sinal.PAPEL);
			}
		});
		btnPapel.setBackground(Color.WHITE);
		btnPapel.setBorder(BorderFactory.createEmptyBorder());
		btnPapel.setIcon(new ImageIcon(
				bufferedImageJankenpon.getSubimage(192, 85, 102, 102).getScaledInstance(65, 65, Image.SCALE_DEFAULT)));

		btnPapel.setBounds(165, 226, 65, 65);
		jogarPanel.add(btnPapel);

		btnTesoura = new JButton("");
		btnTesoura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jogar(Sinal.TESOURA);
			}
		});
		btnTesoura.setBackground(Color.WHITE);
		btnTesoura.setBorder(BorderFactory.createEmptyBorder());
		btnTesoura.setIcon(new ImageIcon(
				bufferedImageJankenpon.getSubimage(159, 190, 102, 102).getScaledInstance(65, 65, Image.SCALE_DEFAULT)));

		btnTesoura.setBounds(121, 88, 65, 65);
		jogarPanel.add(btnTesoura);

		regraPanel = new JPanel();
		bodyPanel.add(regraPanel, "name_932371949313812");
		regraPanel.setLayout(null);

		JLabel lblTituloRegras = new JLabel("Regras");
		lblTituloRegras.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloRegras.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTituloRegras.setBounds(10, 11, 285, 30);
		regraPanel.add(lblTituloRegras);

		JLabel lblRegras = new JLabel("");
		lblRegras.setHorizontalAlignment(SwingConstants.LEFT);
		lblRegras.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRegras.setBounds(10, 52, 285, 215);
		regraPanel.add(lblRegras);
		lblRegras.setText(
				"<html>\r\n<ul>\r\n<li>Tesoura corta papel</li>\r\n<li>Papel cobre pedra</li>\r\n<li>Pedra esmaga lagarto</li>\r\n<li>Lagarto envenena Spock</li>\r\n<li>Spock esmaga (ou derrete) tesoura</li>\r\n<li>Tesoura decapita lagarto</li>\r\n<li>Lagarto come papel</li>\r\n<li>Papel refuta Spock</li>\r\n<li>Spock vaporiza pedra</li>\r\n<li>Pedra quebra tesoura</li>\r\n</ul>\r\n</html");

		btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				bodyPanel.removeAll();
				bodyPanel.add(inicioPanel);
				bodyPanel.repaint();
				bodyPanel.revalidate();
			}
		});
		btnVoltar.setBounds(10, 278, 89, 23);
		regraPanel.add(btnVoltar);

		sobrePanel = new JPanel();
		bodyPanel.add(sobrePanel, "name_936731559376708");
		sobrePanel.setLayout(null);

		btnVoltaSobre = new JButton("Voltar");
		btnVoltaSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bodyPanel.removeAll();
				bodyPanel.add(inicioPanel);
				bodyPanel.repaint();
				bodyPanel.revalidate();
			}
		});
		btnVoltaSobre.setBounds(206, 145, 89, 23);
		sobrePanel.add(btnVoltaSobre);
		
		labelSobre = new JLabel("Sobre");
		labelSobre.setHorizontalAlignment(SwingConstants.CENTER);
		labelSobre.setFont(new Font("Tahoma", Font.PLAIN, 18));
		labelSobre.setBounds(10, 11, 285, 30);
		sobrePanel.add(labelSobre);
		
		lblJogoSimplesDa = new JLabel("<html>\r\nJogo simples, da versão nerd de pedra, papel e tesoura.\r\n<br><br>\r\nCodigo fonte disponível em:\r\n<br>\r\nhttps://github.com/carlos-dias/Pedra-Papel-\r\n<br>Tesoura-Lagarto-Spock/\r\n<br><br>\r\nAutor: Carlos Dias\r\n</html>");
		lblJogoSimplesDa.setBounds(10, 40, 285, 128);
		sobrePanel.add(lblJogoSimplesDa);
		
		lblImagemSobre = new JLabel("");
		lblImagemSobre.setBounds(10, 175, 285, 156);
		lblImagemSobre.setIcon(new ImageIcon(getImagemSobre()));
		sobrePanel.add(lblImagemSobre);

		JPanel leftPanel = new JPanel();
		leftPanel.setBounds(10, 162, 140, 331);
		contentPanel.add(leftPanel);
		leftPanel.setLayout(null);

		lblJogadorUm = new JLabel("Jogador 1");
		lblJogadorUm.setHorizontalAlignment(SwingConstants.CENTER);
		lblJogadorUm.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblJogadorUm.setBounds(0, 11, 140, 35);
		leftPanel.add(lblJogadorUm);

		textJogadorUm = new JTextField();
		textJogadorUm.setHorizontalAlignment(SwingConstants.CENTER);
		textJogadorUm.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textJogadorUm.setBounds(10, 46, 120, 35);
		leftPanel.add(textJogadorUm);
		textJogadorUm.setColumns(10);

		btnConfirmarUm = new JButton("OK");
		btnConfirmarUm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!textJogadorUm.getText().isEmpty()) {
					jogadorUm = new Jogador(textJogadorUm.getText());
					habilitarJogadorUm(false);
					habilitarJogadorDois(true);

					mensagemJogo("Jogador 2 - Insira seu nome!");
				} else {
					mostrarMensagem("Favor, Inserir o nome do jogador!");
				}
			}
		});
		btnConfirmarUm.setBounds(10, 92, 120, 23);
		leftPanel.add(btnConfirmarUm);

		lblVitoriasUm = new JLabel("");
		lblVitoriasUm.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblVitoriasUm.setBounds(10, 157, 120, 14);
		leftPanel.add(lblVitoriasUm);

		lblDerrotasUm = new JLabel("");
		lblDerrotasUm.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDerrotasUm.setBounds(10, 182, 120, 14);
		leftPanel.add(lblDerrotasUm);

		lblEmpatesUm = new JLabel("");
		lblEmpatesUm.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEmpatesUm.setBounds(10, 206, 120, 14);
		leftPanel.add(lblEmpatesUm);

		rightPanel = new JPanel();
		rightPanel.setLayout(null);
		rightPanel.setBounds(470, 162, 140, 331);
		contentPanel.add(rightPanel);

		lblJogadorDois = new JLabel("Jogador 2");
		lblJogadorDois.setHorizontalAlignment(SwingConstants.CENTER);
		lblJogadorDois.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblJogadorDois.setBounds(0, 11, 140, 35);
		rightPanel.add(lblJogadorDois);

		textJogadorDois = new JTextField();
		textJogadorDois.setHorizontalAlignment(SwingConstants.CENTER);
		textJogadorDois.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textJogadorDois.setColumns(10);
		textJogadorDois.setBounds(10, 46, 120, 35);
		rightPanel.add(textJogadorDois);

		btnConfirmarDois = new JButton("OK");
		btnConfirmarDois.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!textJogadorDois.getText().isEmpty()) {
					jogadorDois = new Jogador(textJogadorDois.getText());
					partida.adicionarJogadores(jogadorUm, jogadorDois);

					habilitarJogadorDois(false);

					mensagemQuemJoga();
					renderizarBtnJogadas(true);
					renderizarPlacar(true);
					atualizarPlacar();
				} else {
					mostrarMensagem("Favor, Inserir o nome do jogador!");
				}
			}
		});
		btnConfirmarDois.setBounds(10, 92, 120, 23);
		rightPanel.add(btnConfirmarDois);

		lblVitoriasDois = new JLabel("");
		lblVitoriasDois.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblVitoriasDois.setBounds(10, 157, 120, 14);
		rightPanel.add(lblVitoriasDois);

		lblDerrotasDois = new JLabel("");
		lblDerrotasDois.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDerrotasDois.setBounds(10, 182, 120, 14);
		rightPanel.add(lblDerrotasDois);

		lblEmpatesDois = new JLabel("");
		lblEmpatesDois.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEmpatesDois.setBounds(10, 206, 120, 14);
		rightPanel.add(lblEmpatesDois);

		renderizarJogadores(false);
		renderizarBtnJogadas(false);
		renderizarPlacar(false);
	}

}
